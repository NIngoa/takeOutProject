package com.project.mapper;

import com.github.pagehelper.Page;
import com.project.annotation.AutoFill;
import com.project.dto.SetmealDTO;
import com.project.dto.SetmealPageQueryDTO;
import com.project.entity.Setmeal;
import com.project.enumeration.OperationType;
import com.project.vo.DishItemVO;
import com.project.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    /**
     * 根据条件统计套餐数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
