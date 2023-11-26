package com.project.mapper;

import com.project.annotation.AutoFill;
import com.project.dto.SetmealDTO;
import com.project.entity.Setmeal;
import com.project.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetMealMapper {
    @Select("select count(*) from setmeal where category_id=#{id}")
    Integer selectByCategoryId(Long id);

    @AutoFill(value = OperationType.INSERT)
    void addSetMeal(Setmeal setmeal);
}
