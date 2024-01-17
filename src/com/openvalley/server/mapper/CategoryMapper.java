package com.openvalley.server.mapper;

import com.openvalley.server.entity.Category;

import java.util.List;

public interface CategoryMapper {
    Boolean insert(Category category);//�� category��Ĳ��루���ӣ�
    Boolean delete(Integer id);//�� category���ɾ����ɾ��
    Boolean update(Category category);//��category����޸ģ��ģ�
    Category selectById(Integer id);
    List<Category> selectAllCategory();
}
