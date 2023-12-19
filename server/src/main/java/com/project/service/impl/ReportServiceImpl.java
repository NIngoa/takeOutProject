package com.project.service.impl;

import com.project.entity.Orders;
import com.project.mapper.OrderMapper;
import com.project.service.ReportService;
import com.project.vo.TurnoverReportVO;
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
    /**
     * 统计指定时间区间内的营业额
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate beginDate, LocalDate endDate) throws Exception {
        // 日期列表,用于存放beginDate到endDate的日期
        List<LocalDate>localDateList=new ArrayList<>();
        if (beginDate.isBefore(endDate)||beginDate.isEqual(endDate)) {
            while (beginDate.isBefore(endDate)){
                //日期计算,早于结束日期则加一天
                localDateList.add(beginDate);
                beginDate=beginDate.plusDays(1);
            }
        }else {
            throw new Exception("日期异常");
        }
        localDateList.add(endDate);
        // 日期拼接
        String date = StringUtils.join(localDateList, ",");

        // 营业额列表,用于存放每天的营业额
        List<Double>turnoverList=new ArrayList<>();
        //查询date日期对应的营业额数据，营业额是指订单状态为已完成的订单的总金额
        for (LocalDate localDate : localDateList) {
            LocalDateTime begin = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime end = LocalDateTime.of(localDate, LocalTime.MAX);
            Map map=new HashMap();
            map.put("begin", begin);
            map.put("end", end);
            map.put("status", Orders.COMPLETED);
            Double turnover =orderMapper.turnoverStatistics(map);
            turnover=turnover==null?0.0:turnover;
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
}
