package com.project.service.impl;

import com.project.entity.Orders;
import com.project.mapper.OrderMapper;
import com.project.mapper.UserMapper;
import com.project.service.ReportService;
import com.project.vo.OrderReportVO;
import com.project.vo.TurnoverReportVO;
import com.project.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 统计指定时间区间内的营业额
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate beginDate, LocalDate endDate) throws Exception {
        //获取日期列表
        List<LocalDate> localDateList = getDateList(beginDate, endDate);
        // 日期拼接
        String date = StringUtils.join(localDateList, ",");

        // 营业额列表,用于存放每天的营业额
        List<Double> turnoverList = new ArrayList<>();
        //查询date日期对应的营业额数据，营业额是指订单状态为已完成的订单的总金额
        for (LocalDate localDate : localDateList) {
            LocalDateTime begin = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(localDate, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", begin);
            map.put("end", end);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.turnoverStatistics(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }
        // 营业额拼接
        String turnover = StringUtils.join(turnoverList, ",");

        // 构建返回对象
        TurnoverReportVO turnoverReportVO = TurnoverReportVO.builder()
                .dateList(date)
                .turnoverList(turnover)
                .build();
        return turnoverReportVO;
    }

    /**
     * 统计指定时间区间内的总用户数和每日新增用户数
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) throws Exception {
        //获取日期列表
        List<LocalDate> localDateList = getDateList(begin, end);
        // 总用户列表,用于存放每天的总用户数
        List<Integer> totalUserList = new ArrayList<>();
        //新增用户列表,用于存放每天的新增用户数
        List<Integer> newUserList = new ArrayList<>();
        for (LocalDate localDate : localDateList) {
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);
            Map map = new HashMap();
            //查询endDate日期之前的的用户总量
            map.put("endDate", endTime);
            Integer totalUser = userMapper.getStatistics(map);
            totalUser = totalUser == null ? 0 : totalUser;
            //查询每天的新增用户
            map.put("beginDate", beginTime);
            Integer newUser = userMapper.getStatistics(map);
            newUser = newUser == null ? 0 : newUser;
            //添加到列表中
            totalUserList.add(totalUser);
            newUserList.add(newUser);
        }
        // 日期拼接
        String date = StringUtils.join(localDateList, ",");
        // 总用户拼接
        String totalUser = StringUtils.join(totalUserList, ",");
        // 新增用户拼接
        String newUser = StringUtils.join(newUserList, ",");
        UserReportVO userReportVO = UserReportVO.builder()
                .dateList(date)
                .totalUserList(totalUser)
                .newUserList(newUser)
                .build();
        return userReportVO;
    }

    @Override
    public OrderReportVO orderStatistics(LocalDate begin, LocalDate end) throws Exception {
        //获取日期列表
        List<LocalDate> dateList = getDateList(begin, end);
        //订单数列表,用于存放每天的订单数
        List<Integer> ordersCountList = new ArrayList<>();
        //有效订单数列表,用于存放每天的有效订单数
        List<Integer> validOrderCountList = new ArrayList<>();
        for (LocalDate localDate : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);
            //查询当天订单总数
            Integer ordersCount = getOrders(beginTime, endTime, null);
            ordersCount = ordersCount == null ? 0 : ordersCount;
            ordersCountList.add(ordersCount);
            //查询当天有效订单总数
            Integer validOrdersCount = getOrders(beginTime, endTime, Orders.COMPLETED);
            validOrdersCount = validOrdersCount == null ? 0 : validOrdersCount;
            validOrderCountList.add(validOrdersCount);
        }
        //总订单数
        Integer totalOrders = ordersCountList.stream().reduce(Integer::sum).get();
        //有效订单数
        Integer validTotalOrders = validOrderCountList.stream().reduce(Integer::sum).get();
        //订单完成率
        Double orderCompletionRate = 0.0;
        if (totalOrders != 0) {
            orderCompletionRate = validTotalOrders / totalOrders.doubleValue();
        }
        //构建返回对象
        OrderReportVO orderReportVO = OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(ordersCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrders)
                .validOrderCount(validTotalOrders)
                .orderCompletionRate(orderCompletionRate)
                .build();
        return orderReportVO;
    }


    private Integer getOrders(LocalDateTime begin, LocalDateTime end, Integer status) {
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);
        map.put("status", status);
        Integer count = orderMapper.getOrderStatistics(map);
        return count;
    }

    /**
     * 获取日期列表
     *
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    private List<LocalDate> getDateList(LocalDate begin, LocalDate end) throws Exception {
        // 日期列表,用于存放beginDate到endDate的日期
        List<LocalDate> dateList = new ArrayList<>();
        if (!begin.isAfter(end)) {
            while (begin.isBefore(end)) {
                //日期计算,早于结束日期则加一天
                dateList.add(begin);
                begin = begin.plusDays(1);
            }
        } else {
            throw new Exception("日期异常");
        }
        dateList.add(end);
        return dateList;
    }
}
