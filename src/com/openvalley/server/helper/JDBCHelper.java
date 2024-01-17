package com.openvalley.server.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JDBC 帮助类 <p>
 * 1. 只用于教学 demo , 因此不支持自定义驱动 <br>
 * 2. 此帮助类 , 由于编码不是很规范 , 会有一些 IDEA 提示 , 如果你不想看到这些提示块 , 取消注释 //@SuppressWarnings("all") <br>
 * 3. 当使用 JDBCHelper.select 方法时 , 由于使用了反射映射字段 ,  必须保证 字段名和 实体类属性名一致 , 否则反射失败.
 * 例如 , 如果实体类属性名为 , factoryAddress , 那么数据表字段名也必须为 factoryAddress <br>
 * 4. 当使用 JDBCHelper.select 方法时 , 由于使用了反射映射字段 ,  所以 ,只能使用  SELECT * FROM xxx . 这是不灵活的地方 ,
 * 不过无伤大雅当你开始考虑性能时 , 那时也就不需要这个帮助类了.<br>
 * 5. <b>使用示例:</b><br>
 * <pre>
 *      public static void main(String[] args) {
 *         JDBCHelper jdbcHelper = new JDBCHelper(
 *                 "jdbc:mysql://localhost:3306/medicine",
 *                 "root",
 *                 "123456");
 *
 *         String querySql = "SELECT * FROM category;";
 *
 *         List<Category> result = jdbcHelper.select(querySql, null, Category.class);
 *         result.forEach(System.out::println);
 *
 *         System.out.println();
 *         System.out.println("插入操作 ->>>");
 *         System.out.println();
 *
 *         String insertSql = "INSERT INTO category(id,`name`,description) VALUES (?,?,?);";
 *         String[] insertValues = new String[]{"6","小可爱用药","主治不开心"};
 *         jdbcHelper.insert(insertSql,insertValues);
 *
 *         List<Category> result2 = jdbcHelper.select(querySql, null, Category.class);
 *         result2.forEach(System.out::println);
 *
 *         System.out.println();
 *         System.out.println("更新操作 ->>>");
 *         System.out.println();
 *
 *         String updateSql = "UPDATE category SET  description = ? WHERE id = ?;";
 *         String[] updateValues = new String[]{"主治不可爱","6"};
 *         jdbcHelper.update(updateSql,updateValues);
 *
 *         List<Category> result3 = jdbcHelper.select(querySql, null, Category.class);
 *         result3.forEach(System.out::println);
 *
 *         System.out.println();
 *         System.out.println("删除操作 ->>>");
 *         System.out.println();
 *
 *         String deleteSql = "DELETE FROM category WHERE id = ?;";
 *         String[] deleteValues = new String[]{"6"};
 *         jdbcHelper.delete(deleteSql,deleteValues);
 *
 *         List<Category> result4 = jdbcHelper.select(querySql, null, Category.class);
 *         result4.forEach(System.out::println);
 *     }
 *
 * </pre>
 *
 * @author 王孟沁
 */
//@SuppressWarnings("all")
public class JDBCHelper {

    public JDBCHelper(){
        this("jdbc:mysql://localhost:3306/medicine","root","123456");
    }

    public JDBCHelper(String url, String username, String password) {
        this.url = url.contains("?") ? url : url + URL_SUFFIX;
        this.username = username;
        this.password = password;
    }

    /**
     * 插入数据
     *
     * @param sql          修改数据的 SQL 语句
     * @param insertValues 修改数据的变量 , 可以为 null
     * @return 数据生效条数
     */
    public int insert(String sql, String[] insertValues) {
        return this.update(sql, insertValues);
    }


    /**
     * 删除数据
     *
     * @param sql          删除数据的 SQL 语句
     * @param insertValues 删除数据的变量 , 可以为 null
     * @return 数据生效条数
     */
    public int delete(String sql, String[] insertValues) {
        return this.update(sql, insertValues);
    }

    /**
     * 修改数据
     *
     * @param sql          修改数据的 SQL 语句
     * @param updateValues 修改数据的变量 , 可以为 null
     * @return 数据生效条数
     */
    public int update(String sql, String[] updateValues) {
        ct = getConnection();
        dealPlaceholderOfPrepareStatement(sql, updateValues);
        int in = ERROR;

        try {
            in = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ct, ps, null);
        }

        if (in != ERROR) {
            System.out.println("操作成功，作用了" + in + "条记录！！");
        } else {
            System.out.println("操作失败！！");
        }
        return in;
    }

    /**
     * 查询数据 <p>
     * <b>示例:</b><br>
     * <pre>
     *      String sql = "insert into tb_user (id, login_name, password, name) values (?, ?, ?, ?)";
     *      String[] info2 = {"emo", "qin", "love", "tech"};
     *
     *      其最终发送结果为: insert into tb_user (id, login_name, password, name) values ('emo', 'qin', 'love', 'tech');
     *     </pre>
     *
     * @param sql         查询数据的 SQL 语句
     * @param queryValues 查询SQL的变量 , 可以为 null
     * @return {@link ResultSet}
     */
    public ResultSet select(String sql, String[] queryValues) {
        ct = getConnection();
        dealPlaceholderOfPrepareStatement(sql, queryValues);

        try {
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new NullPointerException("数据库查询失败");
    }


    /**
     * 查询数据 <p>
     * <b>示例:</b><br>
     * <pre>
     *      String sql = "insert into tb_user (id, login_name, password, name) values (?, ?, ?, ?)";
     *      String[] info2 = {"emo", "qin", "love", "tech"};
     *
     *      其最终发送结果为: insert into tb_user (id, login_name, password, name) values ('emo', 'qin', 'love', 'tech');
     *     </pre>
     *
     * @param sql         查询数据的 SQL 语句
     * @param queryValues 查询SQL的变量 , 可以为 null
     * @param clazz       类文件
     * @param <T>         实体类泛型
     * @return 查询列表
     */
    public <T> List<T> select(String sql, String[] queryValues, Class<T> clazz) {
        ResultSet rs = this.select(sql, queryValues);

        List<T> result = new ArrayList<>();
        try {
            while (rs.next()) {
                result.add(resultSetOneToEntity(rs, clazz));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ct, ps, rs);
        }
        return result;
    }


    /**
     * 数据库连接地址
     */
    private final String url;

    /**
     * 数据库连接账号名
     */
    private final String username;

    /**
     * 数据库连接密码
     */
    private final String password;

    /**
     * 数据库连接驱动 ( 只用于教学 , 不支持自定义 )
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 数据库 URL 后缀
     */
    private static final String URL_SUFFIX = "?allowMultiQueries=true&useSSL=false&useUnicode=true&characterEncoding=utf-8&rewriteBatchedStatements=true&autoReconnect=true&serverTimezone=GMT%2B8";

    /**
     * 怎么删改删改失败状态值
     */
    private static final int ERROR = 0;

    private PreparedStatement ps = null;

    private Connection ct = null;

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            System.out.println("数据库加载失败");
            ex.printStackTrace();
        }
    }


    /**
     * 获取数据库连接
     *
     * @return {@link Connection}
     */
    private Connection getConnection() {
        Connection ct = null;
        try {
            ct = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("获取数据库连接失败");
            e.printStackTrace();
        }

        if (ct == null) {
            throw new NullPointerException("数据库连接空指针异常");
        }
        return ct;
    }

    /**
     * 处理占位符问题
     *
     * @param sql    查询 SQL 语句
     * @param values 增删改查询的变量 , 可以为 null
     */
    private void dealPlaceholderOfPrepareStatement(String sql, String[] values) {
        try {
            ps = ct.prepareStatement(sql);

            // 处理占位符的问题
            if (values != null) {
                for (int i = 0, j = values.length; i < j; i++) {
                    ps.setString(1 + i, values[i]);
                }
            }
        } catch (SQLException e) {
            System.out.println(" SQL 预处理:处理占位符失败");
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源
     *
     * @param ct {@link Connection}
     * @param ps {@link PreparedStatement}
     * @param rs {@link ResultSet}
     */
    private void close(Connection ct, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            rs = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (ps != null) {
                ps.close();
            }
            ps = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ct != null) {
                ct.close();
            }
            ct = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private <T> T resultSetOneToEntity(ResultSet rs, Class<T> clazz) {
        try {
            T result = clazz.newInstance();

            Field[] allFields = clazz.getDeclaredFields();

            // 遍历所有字段，获取私有字段的名称
            for (Field field : allFields) {
                if (Modifier.isPrivate(field.getModifiers())) {
                    String fieldName = field.getName();
                    Class<?> type = field.getType();
                    field.setAccessible(true);
                    setFieldValue(rs, result, field, fieldName, type);
                }
            }

            return result;
        } catch (Exception ex) {
            System.out.println("反射对象失败");
            throw new RuntimeException(ex);
        }
    }


    private static <T> void setFieldValue(ResultSet rs, T result, Field field, String fieldName, Class<?> type) throws IllegalAccessException, SQLException {
        if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type)) {
            field.set(result, rs.getInt(fieldName));
        }

        if (String.class.isAssignableFrom(type)) {
            field.set(result, rs.getString(fieldName));
        }

        if (Date.class.isAssignableFrom(type)) {
            field.set(result, rs.getDate(fieldName));
        }

        if (Double.class.isAssignableFrom(type) || double.class.isAssignableFrom(type)) {
            field.set(result, rs.getDouble(fieldName));
        }
    }
}
