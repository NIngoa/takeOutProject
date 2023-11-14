package com.project.service;

import com.project.dto.EmployeeDTO;
import com.project.dto.EmployeeLoginDTO;
import com.project.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void addEmployee(EmployeeDTO employeeDTO);
}
