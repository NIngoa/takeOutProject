package com.project.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.constant.MessageConstant;
import com.project.constant.StatusConstant;
import com.project.dto.SetmealDTO;
import com.project.dto.SetmealPageQueryDTO;
import com.project.entity.Setmeal;
import com.project.entity.SetmealDish;
import com.project.exception.DeletionNotAllowedException;
import com.project.mapper.SetMealDishMapper;
import com.project.mapper.SetMealMapper;
import com.project.result.PageResult;
import com.project.service.SetMealService;
import com.project.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    /**
     * 添加套餐
     *
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void addSetMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        // 设置套餐状态
        setmealDTO.setStatus(StatusConstant.DISABLE);
        // 复制属性
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 添加套餐
        setMealMapper.addSetMeal(setmeal);

        Long id = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        // 设置套餐id
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(id);
        }
        // 添加套餐菜品
        setMealDishMapper.addSetMealDishes(setmealDishes);

    }

    /**
     * 分页查询套餐
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult querySetMealByPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setMealMapper.selectByPage(setmealPageQueryDTO);
        long total = page.getTotal();
        List<SetmealVO> result = page.getResult();
        return new PageResult(total, result);
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    @Transactional
    @Override
    public void deleteSetMeal(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            throw new DeletionNotAllowedException(MessageConstant.OPTION_NOT_FOUND);
        }

        for (Long id : ids) {
            Setmeal setmeal = setMealMapper.selectById(id);
            //起售中的套餐不能删除
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

        //删除套餐表中的数据
        setMealMapper.deleteSetMeal(ids);
        //删除套餐菜品关系表中的数据
        setMealDishMapper.deleteSetMealDishes(ids);
    }

    /**
     * 根据id查询套餐
     *
     * @param setmealId
     * @return
     */
    @Transactional
    @Override
    public SetmealVO querySetMealById(Long setmealId) {
        SetmealVO setmealVO = new SetmealVO();
        Setmeal setmeal = setMealMapper.selectById(setmealId);
        BeanUtils.copyProperties(setmeal, setmealVO);

        List<SetmealDish> setmealDishes = setMealDishMapper.selectBySetMealId(setmealId);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void updateSetMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //1、修改套餐表，执行update
        setMealMapper.updateSetMeal(setmeal);
        //套餐id
        Long setmealid = setmealDTO.getId();
        setMealDishMapper.deleteSetMealDishes(Collections.singletonList(setmealid));

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealid);
        }
        setMealDishMapper.addSetMealDishes(setmealDishes);
    }
}
