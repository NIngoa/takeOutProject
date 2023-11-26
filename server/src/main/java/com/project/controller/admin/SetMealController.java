package com.project.controller.admin;

import com.project.dto.SetmealDTO;
import com.project.result.Result;
import com.project.service.SetMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "套餐相关接口")
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    @PostMapping
    @ApiOperation(value = "新增套餐")
    public Result addSetMeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐:{}", setmealDTO);
        setMealService.addSetMeal(setmealDTO);
        return Result.success();
    }
}
