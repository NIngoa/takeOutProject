package com.project.service;

import com.github.pagehelper.Page;
import com.project.dto.OrdersPageQueryDTO;
import com.project.dto.OrdersPaymentDTO;
import com.project.dto.OrdersSubmitDTO;
import com.project.result.PageResult;
import com.project.vo.OrderPaymentVO;
import com.project.vo.OrderSubmitVO;
import com.project.vo.OrderVO;

public interface OrderService {
    /**
     * 提交订单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    PageResult historyOrders(int page, int pageSize, Integer status);

    OrderVO orderDetail(Long id);

    void cancelOrder(Long id);

    void repetition(Long id);

    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);
}
