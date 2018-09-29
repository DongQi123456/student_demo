package com.controller;

import com.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("movie")
public class MovieController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/findById/{id}")
    public User findById(@PathVariable Long id){
        logger.info(this.getClass().getName() + ".findById(@PathVariable Long id)进入");
        return this.restTemplate.getForObject("http://localhost:8080/findById/" + id, User.class);
    }

}
