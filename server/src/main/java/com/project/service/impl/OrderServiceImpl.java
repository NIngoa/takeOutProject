package com.project.service.impl;

import com.project.constant.MessageConstant;
import com.project.context.BaseContext;
import com.project.dto.OrdersSubmitDTO;
import com.project.entity.AddressBook;
import com.project.entity.OrderDetail;
import com.project.entity.Orders;
import com.project.entity.ShoppingCart;
import com.project.exception.OrderBusinessException;
import com.project.mapper.AddressBookMapper;
import com.project.mapper.OrderDetailMapper;
import com.project.mapper.OrderMapper;
import com.project.mapper.ShoppingCartMapper;
import com.project.service.OrderService;
import com.project.vo.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Transactional
    @Override
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        //检验购物车数据是否为空
        ShoppingCart shoppingCart=new ShoppingCart();
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        //获取购物车数据
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.select(shoppingCart);
        if (shoppingCartList==null&&shoppingCartList.size()==0){
            throw new OrderBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        //检验地址簿数据是否为空
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (addressBook==null){
            throw new OrderBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        //向订单表中插入数据
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setAddress(addressBook.getDetail());
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setUserId(userId);
        orders.setPayStatus(Orders.UN_PAID);
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orderMapper.insert(orders);
        //向订单详情表中插入数据

        List<OrderDetail> orderDetailList =new ArrayList<>();
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail=new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetailList.add(orderDetail);
        }
        orderDetailMapper.insertList(orderDetailList);
        //清空购物车数据
        shoppingCartMapper.deleteAll(userId);
        //封装VO对象
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .orderTime(orders.getOrderTime())
                .build();
        return orderSubmitVO;
    }
}
