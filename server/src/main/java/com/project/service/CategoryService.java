package com.project.service;


import com.project.dto.CategoryDTO;
import com.project.dto.CategoryPageQueryDTO;
import com.project.entity.Category;
import com.project.result.PageResult;

import java.util.List;

public interface CategoryService {

    PageResult selectByPage(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateStatus(Integer status, Long id);

    void addCategory(CategoryDTO categoryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    List<Category> selectByType(Integer type);
}
