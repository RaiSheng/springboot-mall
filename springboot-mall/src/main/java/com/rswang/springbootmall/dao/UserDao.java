package com.rswang.springbootmall.dao;

import com.rswang.springbootmall.dto.UserRegisterRequest;
import com.rswang.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
