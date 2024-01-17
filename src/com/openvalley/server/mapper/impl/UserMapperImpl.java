package com.openvalley.server.mapper.impl;

import com.openvalley.server.entity.User;
import com.openvalley.server.helper.JDBCHelper;
import com.openvalley.server.mapper.UserMapper;

import java.util.List;

public class UserMapperImpl implements UserMapper {
    public static final JDBCHelper JDBC = new JDBCHelper();

    public User selectByUsername(String uname) {
        String sql = "select * from user where username= ?";
        String[] values = new String[]{uname};
        List<User> userList = JDBC.select(sql, values, User.class);
        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    public Boolean insert(User user) {
        String sql = "INSERT INTO user(username,password,type) values(?,?,?)";
        String[] values = new String[]{
                user.getUsername(),
                user.getPassword(),
                user.getType()
        };
        return JDBC.insert(sql, values) > 0;
    }
}
