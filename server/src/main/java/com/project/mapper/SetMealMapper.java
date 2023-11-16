package com.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetMealMapper {
    @Select("select count(*) from setmeal where category_id=#{id}")
    Integer selectByCategoryId(Long id);
}
