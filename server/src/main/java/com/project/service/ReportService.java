package com.project.service;

import com.project.vo.TurnoverReportVO;

import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO turnoverStatistics(LocalDate beginDate, LocalDate endDate) throws Exception;

}
