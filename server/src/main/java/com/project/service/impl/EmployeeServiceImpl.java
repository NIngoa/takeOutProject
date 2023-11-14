package com.project.service.impl;

import com.project.constant.MessageConstant;
import com.project.constant.PasswordConstant;
import com.project.constant.StatusConstant;
import com.project.context.BaseContext;
import com.project.dto.EmployeeDTO;
import com.project.dto.EmployeeLoginDTO;
import com.project.entity.Employee;
import com.project.exception.AccountLockedException;
import com.project.exception.AccountNotFoundException;
import com.project.exception.PasswordErrorException;
import com.project.mapper.EmployeeMapper;
import com.project.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();


        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //3、对密码进行加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //4、返回登录成功的员工对象
        return employee;
    }

    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        //将dto中的数据拷贝到实体对象中
        BeanUtils.copyProperties(employeeDTO, employee);
        //设置状态默认值
        employee.setStatus(StatusConstant.ENABLE);
        //设置默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //设置创建时间
        employee.setCreateTime(LocalDateTime.now());
        //设置更新时间
        employee.setUpdateTime(LocalDateTime.now());
        //设置创建人和更新人
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        //执行添加
        employeeMapper.addEmployee(employee);

    }

}
