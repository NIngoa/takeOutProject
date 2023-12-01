package com.project.controller.user;

import com.project.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@Slf4j
@Api(tags = "店铺相关接口")
@RequestMapping("/user/shop")
public class ShopController {

    public static final String SHOP_STATUS = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 获取店铺状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation(value = "获取店铺状态")
    public Result<Integer> getShopStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS);
        log.info("获取店铺状态:{}",status==1?"营业中":"打烊中");
        return Result.success(status);
    }
}
