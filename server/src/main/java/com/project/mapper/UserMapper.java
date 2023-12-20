package com.project.mapper;

import com.project.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {
    /**
     * 根据openid查询用户
     *
     * @param openid
     * @return
     */
    @Select("select * from user where openid=#{openid}")
    User selectByOpenid(String openid);

    void insert(User user);

    @Select("select * from user where id=#{userId}")
    User getById(Long userId);

    Integer getStatistics(Map map);
}
