package com.service.impl;

import com.entity.Permission;
import com.entity.Role;
import com.entity.User;
import com.repository.UserRepository;
import com.service.UserService;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {
        logger.info("UserServiceImpl.findById(Long id)进入");
        return userRepository.findOne(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Set<String> findRoles(String usernmae) {
        User user = userRepository.findByUsername(usernmae);
        if (user == null) {
            throw new UnknownAccountException();
        }
        Set<String> roleString = new HashSet<String>();
        Set<Role> roles = user.getRoles();
        for (Role role: roles ) {
            roleString.add(role.getRole());
        }
        return roleString;
    }

    @Override
    public Set<String> findPermissions(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException();
        }
        Set<Role> roles = user.getRoles();
        if (roles == null) {
            throw new UnknownAccountException();
        }
        Set<String> permissionString = new HashSet<String>();
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                permissionString.add(permission.getPermission());
            }
        }

        return permissionString;
    }

    @Override
    public User saveUserData(User user) {
        return userRepository.save(user);
    }

}
