package com.project.service;

import com.project.dto.DishDTO;
import com.project.dto.DishPageQueryDTO;
import com.project.result.PageResult;
import com.project.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DishService {
    void addDishWithFlavors(DishDTO dishDTO);

    PageResult queryDishByPage(DishPageQueryDTO dishPageQueryDTO);

    void deleteDish(List<Long> ids);

    DishVO queryDishById(Long id);

    void updateDishWithFlavors(DishDTO dishDTO);
}
