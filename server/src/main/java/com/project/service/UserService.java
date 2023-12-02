package com.project.service;

import com.project.dto.UserLoginDTO;
import com.project.entity.Setmeal;
import com.project.entity.User;

import java.util.List;

public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);

}
