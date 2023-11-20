package com.project.service;

import com.project.dto.DishDTO;
import com.project.dto.DishPageQueryDTO;
import com.project.result.PageResult;
import org.springframework.stereotype.Service;

public interface DishService {
    void addDishWithFlavors(DishDTO dishDTO);

    PageResult queryDishByPage(DishPageQueryDTO dishPageQueryDTO);

}
