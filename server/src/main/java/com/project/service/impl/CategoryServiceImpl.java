package com.project.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.constant.MessageConstant;
import com.project.constant.StatusConstant;
import com.project.dto.CategoryDTO;
import com.project.dto.CategoryPageQueryDTO;
import com.project.entity.Category;
import com.project.exception.DeletionNotAllowedException;
import com.project.mapper.CategoryMapper;
import com.project.mapper.DishMapper;
import com.project.mapper.SetMealMapper;
import com.project.result.PageResult;
import com.project.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setMealMapper;

    /**
     * 分页查询分类
     *
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult selectByPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        //分页查询
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        //查询
        Page<Category> page = categoryMapper.selectByPage(categoryPageQueryDTO);
        //封装返回值
        long total = page.getTotal();
        List<Category> result = page.getResult();
        //返回结果
        return new PageResult(total, result);
    }

    /**
     * 修改分类状态
     *
     * @param status
     * @param id
     */
    @Override
    public void updateStatus(Integer status, Long id) {
        //修改状态
        Category category = Category.builder()
//                .updateTime(LocalDateTime.now())
//                .updateUser(BaseContext.getCurrentId())
                .status(status)
                .id(id)
                .build();
        //修改
        categoryMapper.update(category);
    }

    /**
     * 添加分类
     *
     * @param categoryDTO
     */
    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        //复制属性
        BeanUtils.copyProperties(categoryDTO, category);
        //设置创建时间
//        category.setCreateTime(LocalDateTime.now());
//        //设置创建人
//        category.setCreateUser(BaseContext.getCurrentId());
//        //设置更新时间
//        category.setUpdateTime(LocalDateTime.now());
//        //设置更新人
//        category.setUpdateUser(BaseContext.getCurrentId());
        //设置状态
        category.setStatus(StatusConstant.DISABLE);
        categoryMapper.addCategory(category);
    }

    /**
     * * 修改分类
     *
     * @param categoryDTO
     */
    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .type(categoryDTO.getType())
//                .updateTime(LocalDateTime.now())
//                .updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.update(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Integer dishCount = dishMapper.selectByCategoryId(id);
        if (dishCount > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        Integer SetMealCount = setMealMapper.selectByCategoryId(id);
        if (SetMealCount > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteById(id);
    }

    @Override
    public List<Category> selectByType(Integer type) {
        List<Category> category = categoryMapper.selectByType(type);
        return category;
    }

}
