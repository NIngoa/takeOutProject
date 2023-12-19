package com.project.mapper;

import com.github.pagehelper.Page;
import com.project.dto.OrdersPageQueryDTO;
import com.project.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);


    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id=#{id}")
    Orders selectByOrderId(Long id);

    Integer getStatistics(Integer status);

    /**
     * 查询超时订单
     *
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select * from orders where status=#{status} and order_time < #{orderTime}")
    List<Orders> selectProcessTimeout(Integer status, LocalDateTime orderTime);

    /**
     * 批量修改订单状态
     *
     * @param idList
     * @param status
     */
    void updateList(List<Long> idList, Integer status);

    Double turnoverStatistics(Map map);
}
