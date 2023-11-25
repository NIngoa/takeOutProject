package com.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    List<Long> selectSetMealidsByDishIds(List<Long> ids);
}
