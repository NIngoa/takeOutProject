package com.project.service;

import com.project.dto.OrdersSubmitDTO;
import com.project.vo.OrderSubmitVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
