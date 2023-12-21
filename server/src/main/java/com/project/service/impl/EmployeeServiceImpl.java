package com.project.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.constant.MessageConstant;
import com.project.constant.PasswordConstant;
import com.project.constant.StatusConstant;
import com.project.context.BaseContext;
import com.project.dto.EmployeeDTO;
import com.project.dto.EmployeeLoginDTO;
import com.project.dto.EmployeePageQueryDTO;
import com.project.dto.PasswordEditDTO;
import com.project.entity.Employee;
import com.project.exception.AccountLockedException;
import com.project.exception.AccountNotFoundException;
import com.project.exception.PasswordErrorException;
import com.project.mapper.EmployeeMapper;
import com.project.result.PageResult;
import com.project.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.beans.Beans;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 新增员工
     *
     * @param employeeDTO
     */
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

    /**
     * 分页查询员工
     *
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult selectByPage(EmployeePageQueryDTO employeePageQueryDTO) {
        //1、使用pageHelper分页插件
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        //2、执行查询
        Page<Employee> page = employeeMapper.selectByPage(employeePageQueryDTO);
        //3、封装返回值
        long total = page.getTotal();
        List<Employee> result = page.getResult();
        return new PageResult(total, result);
    }

    /**
     * 修改员工状态
     *
     * @param status
     * @param id
     */
    @Override
    public void updateStatus(Integer status, Long id) {
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
//                .updateTime(LocalDateTime.now())
//                .updateUser(BaseContext.getCurrentId())
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.selectById(id);
        return employee;
    }

    /**
     * 修改员工信息
     *
     * @param employeeDTO
     */
    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }

    /**
     * 修改员工密码
     *
     * @param passwordEditDTO
     */
    @Override
    public void editPasswordEmployee(PasswordEditDTO passwordEditDTO) {
        passwordEditDTO.setEmpId(BaseContext.getCurrentId());
        Long empId = passwordEditDTO.getEmpId();
        Employee employee = employeeMapper.selectById(empId);
        //1、判断旧密码是否正确
        String nowPassword = employee.getPassword();
        String oldPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getOldPassword().getBytes());
        if (oldPassword.equals(nowPassword)) {
            //2、修改密码
            String newPassword = passwordEditDTO.getNewPassword();
            employeeMapper.update(Employee.builder()
                    .password(DigestUtils.md5DigestAsHex(newPassword.getBytes()))
                    .id(empId)
                    .build());
        }

    }
}
