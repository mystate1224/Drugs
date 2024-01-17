package com.openvalley.server.mapper;

import com.openvalley.server.entity.User;

public interface UserMapper {
    User selectByUsername(String uname);
    Boolean insert(User user);
}
