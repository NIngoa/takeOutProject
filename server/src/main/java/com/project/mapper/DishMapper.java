package com.project.mapper;

import com.github.pagehelper.Page;
import com.project.annotation.AutoFill;
import com.project.dto.DishPageQueryDTO;
import com.project.entity.Dish;
import com.project.enumeration.OperationType;
import com.project.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
@Select("select count(*) from dish where category_id=#{id}")
    Integer selectByCategoryId(Long id);
@AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO>queryDishByPage(DishPageQueryDTO dishPageQueryDTO);
}
