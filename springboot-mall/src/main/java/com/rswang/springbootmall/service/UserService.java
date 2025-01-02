package com.rswang.springbootmall.service;

import com.rswang.springbootmall.dto.UserLoginRequest;
import com.rswang.springbootmall.dto.UserRegisterRequest;
import com.rswang.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);
}
