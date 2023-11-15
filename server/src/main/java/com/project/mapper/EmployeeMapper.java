package com.project.mapper;

import com.github.pagehelper.Page;
import com.project.dto.EmployeeDTO;
import com.project.dto.EmployeePageQueryDTO;
import com.project.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工
     *
     * @param employee
     */
    @Insert("insert into employee(name,username,password,phone,sex,id_number,status,create_time,update_time,create_user,update_user) values(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void addEmployee(Employee employee);

    /**
     *员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> selectByPage(EmployeePageQueryDTO employeePageQueryDTO);

    void update(Employee employee);
@Select("select * from employee where id=#{id}")
    Employee selectById(Long id);
}
