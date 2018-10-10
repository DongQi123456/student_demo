package com.service;

import com.entity.Role;
import com.entity.User;

import java.sql.SQLException;
import java.util.Set;

public interface UserService {

    /**
     * 通过id查询用户信息
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 通过username获取用户信息
     * @param username
     * @return
     * @throws SQLException
     */
    User findByUsername(String username);

    /**
     * 通过username查找用户角色
     * @param usernmae
     * @return
     */
    Set<String> findRoles(String usernmae);

    /**
     * 通过username查询用户权限
     * @param username
     * @return
     */
    Set<String> findPermissions(String username);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    User saveUserData(User user);
}
