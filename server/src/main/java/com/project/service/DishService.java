package com.project.service;

import com.project.dto.DishDTO;
import org.springframework.stereotype.Service;

public interface DishService {
    void addDishWithFlavors(DishDTO dishDTO);
}
