package com.project.controller.admin;

import com.project.result.Result;
import com.project.service.ReportService;
import com.project.vo.OrderReportVO;
import com.project.vo.SalesTop10ReportVO;
import com.project.vo.TurnoverReportVO;
import com.project.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@RestController
@Api(tags = "数据统计相关接口")
@Slf4j
@RequestMapping("/admin/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 营业额统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation(value = "营业额统计")
    public Result<TurnoverReportVO> turnover(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) throws Exception {
        log.info("营业额统计begin:{},end:{}", begin, end);
        TurnoverReportVO turnoverReportVO = reportService.turnoverStatistics(begin, end);
        return Result.success(turnoverReportVO);
    }

    /**
     * 用户统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @GetMapping("/userStatistics")
    @ApiOperation(value = "用户统计")
    public Result<UserReportVO> userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) throws Exception {
        log.info("用户统计begin:{},end:{}", begin, end);
        UserReportVO userReportVO = reportService.userStatistics(begin, end);
        return Result.success(userReportVO);
    }

    /**
     * 订单统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @GetMapping("/ordersStatistics")
    @ApiOperation(value = "订单统计")
    public Result<OrderReportVO> ordersStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) throws Exception {
        log.info("订单统计begin:{},end:{}", begin, end);
        OrderReportVO orderReportVO = reportService.orderStatistics(begin, end);
        return Result.success(orderReportVO);
    }

    /**
     * 查询销量排名top10
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @GetMapping("/top10")
    @ApiOperation(value = "查询销量排名top10")
    public Result<SalesTop10ReportVO> top10(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) throws Exception {
        SalesTop10ReportVO salesTop10ReportVO = reportService.top10(begin, end);
        return Result.success(salesTop10ReportVO);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        reportService.export(response);
    }
}
