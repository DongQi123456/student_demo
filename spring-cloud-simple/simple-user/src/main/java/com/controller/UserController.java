package com.controller;

import com.entity.User;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;

    /**
     * TODO 通过id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public User findById(@PathVariable("id") Long id){
        logger.info(this.getClass().getName() + ".findById(@RequestBody Long id)进入");
        return userService.findById(id);
    }

    @GetMapping("/eureka-instance")
    public String serviceUrl(){
        InstanceInfo instanceInfo = this.eurekaClient.getNextServerFromEureka("SIMPLE-USER", false);
        return instanceInfo.getHomePageUrl();
    }


}
