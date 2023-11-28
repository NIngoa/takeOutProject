package com.project.mapper;

import com.github.pagehelper.Page;
import com.project.annotation.AutoFill;
import com.project.dto.SetmealDTO;
import com.project.dto.SetmealPageQueryDTO;
import com.project.entity.Setmeal;
import com.project.enumeration.OperationType;
import com.project.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealMapper {
    @Select("select count(*) from setmeal where category_id=#{id}")
    Integer selectByCategoryId(Long id);

    @AutoFill(value = OperationType.INSERT)
    void addSetMeal(Setmeal setmeal);

    Page<SetmealVO> selectByPage(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteSetMeal(List<Long> ids);

    Setmeal selectById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void updateSetMeal(Setmeal setmeal);
}
