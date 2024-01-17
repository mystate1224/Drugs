package com.openvalley.server.mapper.impl;

import com.openvalley.server.entity.Category;
import com.openvalley.server.helper.JDBCHelper;
import com.openvalley.server.mapper.CategoryMapper;

import java.util.List;

public class CategoryMapperImpl implements CategoryMapper {
    public static final JDBCHelper JDBC = new JDBCHelper();

    @Override
    public Boolean insert(Category category) {
        String sql="insert into category(name,description) values(?,?)";
        String[] values={
                category.getName(),
                category.getDescription()
        };
        return JDBC.insert(sql,values)>0;
    }

    @Override
    public Boolean delete(Integer id) {//根据id来删除记录
        String sql="delete from category where id=?";
        String[] values=new String[]{String.valueOf(id)};
        return JDBC.delete(sql,values)>0;
    }

    @Override
    public Boolean update(Category category) {
        String sql="update category set name=?,description=? where id=?";
        String[] values={
                category.getName(),
                category.getDescription(),
                String.valueOf(category.getId())
        };
        return JDBC.update(sql,values)>0;
    }

    @Override
    public Category selectById(Integer id) {
        String sql="select * from category where id=?";
        String[] values={String.valueOf(id)};
        List<Category>categoryList=JDBC.select(sql,values,Category.class);
        return categoryList.isEmpty()?null:categoryList.get(0);
    }

    @Override
    public List<Category> selectAllCategory() {
        String sql="select * from category";
        return  JDBC.select(sql,null, Category.class);
    }
}
