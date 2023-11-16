package com.project.mapper;

import com.github.pagehelper.Page;
import com.project.dto.CategoryPageQueryDTO;
import com.project.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> selectByPage(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 更新操作
     *
     * @param category
     */
    void update(Category category);

    /**
     * 新增分类
     *
     * @param category
     */
    void addCategory(Category category);

    void deleteById(Long id);
}
