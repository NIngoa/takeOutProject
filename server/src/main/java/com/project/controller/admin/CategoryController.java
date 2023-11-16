package com.project.controller.admin;

import com.project.dto.CategoryDTO;
import com.project.dto.CategoryPageQueryDTO;
import com.project.result.PageResult;
import com.project.result.Result;
import com.project.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分类分页查询")
    public Result<PageResult> selectByPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.selectByPage(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 修改分类状态
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改分类状态")
    public Result categoryStatus(@PathVariable Integer status, Long id) {
        categoryService.updateStatus(status, id);
        return Result.success();
    }

    /**
     * 新增分类
     *
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    @PutMapping
    @ApiOperation(value = "修改分类")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除分类")
    public Result deleteCategory(Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }

}
