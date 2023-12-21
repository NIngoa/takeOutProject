package com.project.mapper;

import com.github.pagehelper.Page;
import com.project.annotation.AutoFill;
import com.project.dto.DishDTO;
import com.project.dto.DishPageQueryDTO;
import com.project.entity.Dish;
import com.project.enumeration.OperationType;
import com.project.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {
    /**
     * 根据分类id查询菜品数量
     *
     * @param id
     * @return
     */
    @Select("select count(*) from dish where category_id=#{id}")
    Integer selectByCategoryId(Long id);

    /**
     * 新增菜品
     *
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> queryDishByPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据菜品id查询菜品状态
     *
     * @param ids
     * @return
     */
    List<Integer> selectStatusByIds(List<Long> ids);

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    Dish selectById(Long id);

    /**
     * 修改菜品
     *
     * @param dish
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateDishById(Dish dish);

    /**
     * 根据分类id查询菜品
     *
     * @param dish
     * @return
     */
    List<Dish> selectDishByCategoryId(Dish dish);

    /**
     * 修改菜品状态
     *
     * @param dish
     */
    void updateStatus(Dish dish);

    /**
     * 根据条件统计菜品数量
     *
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
