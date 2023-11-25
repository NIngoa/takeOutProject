package com.project.mapper;

import com.project.dto.DishDTO;
import com.project.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据id删除
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据菜品id查询该菜品的口味
     * @param id
     * @return
     */
    List<DishFlavor> selectByDishId(Long id);

}
