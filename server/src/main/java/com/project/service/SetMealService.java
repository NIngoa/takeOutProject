package com.project.service;

import com.project.dto.SetmealDTO;
import com.project.dto.SetmealPageQueryDTO;
import com.project.entity.Setmeal;
import com.project.result.PageResult;
import com.project.vo.DishItemVO;
import com.project.vo.SetmealVO;

import java.util.List;

public interface SetMealService {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDTO
     */
    void addSetMeal(SetmealDTO setmealDTO);

    PageResult querySetMealByPage(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteSetMeal(List<Long> ids);

    SetmealVO querySetMealById(Long id);

    void updateSetMeal(SetmealDTO setmealDTO);

    void updateStatus(Integer status, Long id);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
