package com.project.mapper;

import com.project.annotation.AutoFill;
import com.project.entity.SetmealDish;
import com.project.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    List<Long> selectSetMealidsByDishIds(List<Long> ids);

    void addSetMealDishes(List<SetmealDish> setmealDishes);

    void deleteSetMealDishes(List<Long> ids);

    List<SetmealDish> selectBySetMealId(Long id);

}
