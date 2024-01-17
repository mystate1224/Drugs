package com.openvalley.server.mapper;

import com.openvalley.server.entity.Category;

import java.util.List;

public interface CategoryMapper {
    Boolean insert(Category category);//对 category表的插入（增加）
    Boolean delete(Integer id);//对 category表的删除（删）
    Boolean update(Category category);//对category表的修改（改）
    Category selectById(Integer id);
    List<Category> selectAllCategory();
}
