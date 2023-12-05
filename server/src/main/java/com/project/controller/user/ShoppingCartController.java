package com.project.controller.user;

import com.project.dto.ShoppingCartDTO;
import com.project.entity.ShoppingCart;
import com.project.result.Result;
import com.project.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "购物车相关接口")
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加购物车")
    public Result addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车:{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查看购物车")
    public Result<List<ShoppingCart>> listShoppingCart() {
        log.info("查看购物车");
        List<ShoppingCart>list=shoppingCartService.listShoppingCart();
        return Result.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    @ApiOperation(value = "清空购物车")
    public Result deleteShoppingCart() {
        log.info("清空购物车");
        shoppingCartService.deleteShoppingCart();
        return Result.success();
    }

    @PostMapping("/sub")
    @ApiOperation(value = "删除购物车中的一个商品")
    public Result subShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("删除购物车中的一个商品:{}", shoppingCartDTO);
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }
}
