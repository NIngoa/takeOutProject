package com.project.controller.admin;

import com.project.dto.DishDTO;
import com.project.dto.DishPageQueryDTO;
import com.project.entity.Dish;
import com.project.result.PageResult;
import com.project.result.Result;
import com.project.service.DishService;
import com.project.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Api(tags = "菜品相关接口")
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     *
     * @param dishDTO
     * @return
     */

    @PostMapping
    @ApiOperation(value = "新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}", dishDTO);
        dishService.addDishWithFlavors(dishDTO);
        return Result.success();
    }

    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询菜品")
    public Result<PageResult> queryDishByPage(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品,dishPageQueryDTO:{}", dishPageQueryDTO);
        PageResult pageResult = dishService.queryDishByPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "批量删除菜品")
    public Result deleteDish(@RequestParam List<Long> ids) {
        log.info("批量删除菜品,ids:{}", ids);
        dishService.deleteDish(ids);
        return Result.success();
    }

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询菜品")
    public Result<DishVO> queryDishById(@PathVariable Long id) {
        log.info("根据id查询菜品,id:{}", id);
        DishVO dishVO = dishService.queryDishById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     *
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改菜品")
    public Result updateDish(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品:{}", dishDTO);
        dishService.updateDishWithFlavors(dishDTO);
        return Result.success();
    }


    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据分类id查询菜品")
    public Result<List> queryDishByCategoryId(Long categoryId) {
        log.info("根据分类id查询菜品,categoryId:{}", categoryId);
        List<Dish>dish = dishService.queryDishByCategoryId(categoryId);
        return Result.success(dish);
    }

    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改菜品状态")
    public Result dishStatus(@PathVariable Integer status, Long id) {
        log.info("修改菜品状态,status:{},id:{}", status, id);
        dishService.updateDishStatusById(status,id);
        return Result.success();
    }

}
