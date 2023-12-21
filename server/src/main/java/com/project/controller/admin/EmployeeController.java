package com.project.controller.admin;

import com.project.constant.JwtClaimsConstant;
import com.project.dto.EmployeeDTO;
import com.project.dto.EmployeeLoginDTO;
import com.project.dto.EmployeePageQueryDTO;
import com.project.dto.PasswordEditDTO;
import com.project.entity.Employee;
import com.project.properties.JwtProperties;
import com.project.result.PageResult;
import com.project.result.Result;
import com.project.service.EmployeeService;
import com.project.utils.JwtUtil;
import com.project.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工退出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     *
     * @param employeeDTO 员工信息数据传输对象
     * @return 操作结果
     */
    @PostMapping
    @ApiOperation(value = "新增员工")
    public Result addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工信息:{}", employeeDTO);
        employeeService.addEmployee(employeeDTO);
        return Result.success();
    }

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO 员工分页查询数据传输对象
     * @return 操作结果
     */
    @GetMapping("/page")
    @ApiOperation(value = "员工分页查询")
    public Result<PageResult> selectByPage(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("分页查询员工信息:{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.selectByPage(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 修改员工状态
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改员工状态")
    public Result employeeStatus(@PathVariable  Integer status,Long id) {
        log.info("修改员工状态：{},{}",status,id);
        employeeService.updateStatus(status,id);
        return Result.success();
    }

        /**
     * 根据id查询员工信息
     * @param id 员工id
     * @return 员工信息
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询员工信息")
    public Result<Employee> selectById(@PathVariable Long id) {
        log.info("查询员工信息：{}",id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 修改员工信息
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改员工信息")
    public Result updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("修改员工信息：{}",employeeDTO);
        employeeService.updateEmployee(employeeDTO);
        return Result.success();
    }

    /**
     * 修改员工密码
     * @param passwordEditDTO
     * @return
     */
    @PutMapping("/editPassword")
    @ApiOperation(value = "修改员工密码")
    public Result editPassword(@RequestBody PasswordEditDTO passwordEditDTO) {
        log.info("修改员工密码：{}",passwordEditDTO);
        employeeService.editPasswordEmployee(passwordEditDTO);
        return Result.success();
    }
}
