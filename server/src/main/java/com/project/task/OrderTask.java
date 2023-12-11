package com.project.task;

import com.project.constant.MessageConstant;
import com.project.entity.Orders;
import com.project.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单
     */
    @Scheduled(cron = "0 * * * * ?")//每分钟触发一次
//    @Scheduled(cron = "0/10 * * * * ?")
    public void processTimeoutTask() {
        log.info("处理超时订单:{}", LocalDateTime.now());
        //查询超时订单
        LocalDateTime time = LocalDateTime.now().minusMinutes(15);
        List<Orders> ordersList = orderMapper.selectProcessTimeout(Orders.PENDING_PAYMENT, time);
        //修改订单状态
        if (ordersList != null && ordersList.size() > 0) {
            for (Orders order : ordersList) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelTime(LocalDateTime.now());
                order.setCancelReason(MessageConstant.ORDER_TIMEOUT_CANCEL);
                orderMapper.update(order);
            }
        }
    }

    /**
     * 处理处于派送中的订单
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点执行一次
//    @Scheduled(cron = "0/5 * * * * ?")
    public void processDelivery() {
        log.info("定时处理处于派送中的订单:{}", LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().minusMinutes(60);

        List<Orders> ordersList = orderMapper.selectProcessTimeout(Orders.DELIVERY_IN_PROGRESS, time);

        if (ordersList != null && ordersList.size() > 0) {
            for (Orders order : ordersList) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
