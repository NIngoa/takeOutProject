package com.project.mapper;

import com.project.dto.OrdersSubmitDTO;
import com.project.entity.Orders;
import com.project.vo.OrderSubmitVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);
}
