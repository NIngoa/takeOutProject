package com.project.service.impl;

import com.project.constant.MessageConstant;
import com.project.constant.StatusConstant;
import com.project.dto.SetmealDTO;
import com.project.entity.Setmeal;
import com.project.entity.SetmealDish;
import com.project.mapper.SetMealDishMapper;
import com.project.mapper.SetMealMapper;
import com.project.service.SetMealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    /**
     * 添加套餐
     *
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void addSetMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        // 设置套餐状态
        setmealDTO.setStatus(StatusConstant.DISABLE);
        // 复制属性
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 添加套餐
        setMealMapper.addSetMeal(setmeal);

        Long id = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        // 设置套餐id
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(id);
        }
        // 添加套餐菜品
        setMealDishMapper.addSetMealDishes(setmealDishes);

    }
}
