package com.controller;

import com.entity.User;
import com.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * TODO 通过id查询用户信息
     * @param id
     * @return
     */
    @RequestMapping("/findById/{id}")
    public User findById(@PathVariable("id") Long id){
        logger.info(this.getClass().getName() + ".findById(@RequestBody Long id)进入");
        return userService.findById(id);
    }


}
