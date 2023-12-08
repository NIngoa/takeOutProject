package com.project.service;

import com.project.dto.OrdersPaymentDTO;
import com.project.dto.OrdersSubmitDTO;
import com.project.result.PageResult;
import com.project.vo.OrderPaymentVO;
import com.project.vo.OrderSubmitVO;

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

}
