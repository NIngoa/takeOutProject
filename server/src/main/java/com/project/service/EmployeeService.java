package com.project.service;

import com.project.dto.EmployeeDTO;
import com.project.dto.EmployeeLoginDTO;
import com.project.dto.EmployeePageQueryDTO;
import com.project.entity.Employee;
import com.project.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void addEmployee(EmployeeDTO employeeDTO);

    PageResult selectByPage(EmployeePageQueryDTO employeePageQueryDTO);

    void updateStatus(Integer status,Long id);

    Employee getById(Long id);

    void updateEmployee(EmployeeDTO employeeDTO);

}
