package com.project.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.constant.MessageConstant;
import com.project.constant.StatusConstant;
import com.project.dto.DishDTO;
import com.project.dto.DishPageQueryDTO;
import com.project.entity.Dish;
import com.project.entity.DishFlavor;
import com.project.exception.DeletionNotAllowedException;
import com.project.mapper.DishFlavorMapper;
import com.project.mapper.DishMapper;
import com.project.mapper.SetMealDishMapper;
import com.project.result.PageResult;
import com.project.service.DishService;
import com.project.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    @Transactional
    @Override
    public void addDishWithFlavors(DishDTO dishDTO) {
        //新建菜品对象
        Dish dish = new Dish();
        //拷贝属性
        BeanUtils.copyProperties(dishDTO, dish);
        //插入菜品
        dishMapper.insert(dish);
        //返回insert语句生成的主键值
        Long id = dish.getId();
        //批量插入口味（如果有）
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors.size() > 0 && flavors != null) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(id));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult queryDishByPage(DishPageQueryDTO dishPageQueryDTO) {
        //分页参数
        int pageNum = dishPageQueryDTO.getPage();
        int pageSize = dishPageQueryDTO.getPageSize();
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        Page<DishVO> page = dishMapper.queryDishByPage(dishPageQueryDTO);
        //封装返回值
        long total = page.getTotal();
        List<DishVO> result = page.getResult();

        return new PageResult(total, result);
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    @Transactional
    @Override
    public void deleteDish(List<Long> ids) {
        //批量查询菜品状态,如果有菜品在售,则不允许删除
        List<Integer> statusList = dishMapper.selectStatusByIds(ids);
        for (Integer status : statusList) {
            if (status.equals(StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //批量删除套餐菜品关联表
        List<Long> setMealIds = setMealDishMapper.selectSetMealidsByDishIds(ids);
        if (setMealIds.size() > 0 && setMealIds != null) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //批量删除菜品
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByIds(ids);
    }

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    @Override
    public DishVO queryDishById(Long id) {
        //查询菜品
        Dish dish = dishMapper.selectById(id);
        //查询菜品口味
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectByDishId(id);
        //封装返回值
        DishVO dishVO = new DishVO();
        //拷贝属性
        BeanUtils.copyProperties(dish, dishVO);
        //设置菜品口味
        dishVO.setFlavors(dishFlavors);
        //返回
        return dishVO;
    }

    /**
     * 修改菜品
     *
     * @param dishDTO
     */
    @Transactional
    @Override
    public void updateDishWithFlavors(DishDTO dishDTO) {
        //新建菜品对象
        Dish dish = new Dish();
        //拷贝属性
        BeanUtils.copyProperties(dishDTO, dish);
        //更新菜品
        dishMapper.updateDishById(dish);
        //批量删除菜品口味
        dishFlavorMapper.deleteByIds(Collections.singletonList(dishDTO.getId()));
        List<DishFlavor> flavors = dishDTO.getFlavors();
        //批量插入菜品口味
        if (flavors.size() > 0 && flavors != null) {
            //设置菜品id
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishDTO.getId());
            }
            //批量插入菜品口味
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<Dish> queryDishByCategoryId(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        List<Dish> dishList = dishMapper.selectDishByCategoryId(dish);
        return dishList;
    }

    @Override
    public void updateDishStatusById(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.updateStatus(dish);
    }
}
