package com.project.service.impl;

import com.project.dto.DishDTO;
import com.project.entity.Dish;
import com.project.entity.DishFlavor;
import com.project.mapper.DishFlavorMapper;
import com.project.mapper.DishMapper;
import com.project.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Beans;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    @Transactional
    @Override
    public void addDishWithFlavors(DishDTO dishDTO) {
        //新建菜品对象
        Dish dish = new Dish();
        //拷贝属性
        BeanUtils.copyProperties(dishDTO, dish);
        //插入菜品
        dishMapper.insert(dish);
        //返回insert语句生成的主键值
        Long id = dish.getId();
        //批量插入口味（如果有）
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors.size() > 0 && flavors != null) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(id));
            dishFlavorMapper.insertBatch(flavors);
        }
    }
}
