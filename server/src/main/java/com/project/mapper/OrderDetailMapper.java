package com.project.mapper;

import com.project.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    void insertList(List<OrderDetail> orderDetailList);
}
