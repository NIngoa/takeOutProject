package com.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
@Select("select count(*) from dish where category_id=#{id}")
    Integer selectByCategoryId(Long id);
}
