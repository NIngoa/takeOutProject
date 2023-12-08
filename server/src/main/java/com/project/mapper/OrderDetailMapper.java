package com.project.mapper;

import com.project.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    void insertList(List<OrderDetail> orderDetailList);
@Select("select * from order_detail where id=#{orderId}")
    List<OrderDetail> selectByOrderId(Long orderId);
}

