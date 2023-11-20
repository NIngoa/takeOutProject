package com.project.mapper;

import com.project.annotation.AutoFill;
import com.project.dto.DishDTO;
import com.project.entity.Dish;
import com.project.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
@Select("select count(*) from dish where category_id=#{id}")
    Integer selectByCategoryId(Long id);
@AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

}
