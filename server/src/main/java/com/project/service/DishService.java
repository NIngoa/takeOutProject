package com.project.service;

import com.project.dto.DishDTO;
import com.project.dto.DishPageQueryDTO;
import com.project.entity.Dish;
import com.project.result.PageResult;
import com.project.vo.DishVO;

import java.util.List;

public interface DishService {
    void addDishWithFlavors(DishDTO dishDTO);

    PageResult queryDishByPage(DishPageQueryDTO dishPageQueryDTO);

    void deleteDish(List<Long> ids);

    DishVO queryDishById(Long id);

    void updateDishWithFlavors(DishDTO dishDTO);

    List<Dish> queryDishByCategoryId(Long categoryId);

    void updateDishStatusById(Integer status, Long id);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> selectWithFlavor(Dish dish);
}
