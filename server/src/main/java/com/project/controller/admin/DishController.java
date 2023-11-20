package com.project.controller.admin;

import com.project.dto.DishDTO;
import com.project.dto.DishPageQueryDTO;
import com.project.result.PageResult;
import com.project.result.Result;
import com.project.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "菜品相关接口")
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
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
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询菜品")
    public Result<PageResult> queryDishByPage(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品,dishPageQueryDTO:{}",dishPageQueryDTO);
        PageResult pageResult =dishService.queryDishByPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }
}
