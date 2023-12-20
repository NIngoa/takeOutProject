package com.project.service;

import com.project.vo.OrderReportVO;
import com.project.vo.TurnoverReportVO;
import com.project.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO turnoverStatistics(LocalDate beginDate, LocalDate endDate) throws Exception;

    UserReportVO userStatistics(LocalDate begin, LocalDate end) throws Exception;

    OrderReportVO orderStatistics(LocalDate begin, LocalDate end) throws Exception;
}
