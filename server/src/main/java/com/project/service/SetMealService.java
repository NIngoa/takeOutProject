package com.project.service;

import com.project.dto.SetmealDTO;
import org.springframework.stereotype.Service;

public interface SetMealService {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDTO
     */
    void addSetMeal(SetmealDTO setmealDTO);
}
