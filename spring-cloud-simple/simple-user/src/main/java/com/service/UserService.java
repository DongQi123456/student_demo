package com.service;

import com.entity.User;

public interface UserService {

    /**
     * 通过id查询用户信息
     * @param id
     * @return
     */
    User findById(Long id);
}
