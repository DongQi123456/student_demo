package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.entity.User;
import com.entity.UserInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;

    /**
     * 登录方法
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
    @ResponseBody
    public String ajaxLogin(UserInfo userInfo){
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUsername(), userInfo.getPassword());
        try {
            subject.login(token);
            jsonObject.put("token", subject.getSession().getId());
            jsonObject.put("msg", "登录成功");
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("msg", "密码错误");
        } catch (LockedAccountException e) {
            jsonObject.put("msg", "登录失败， 该用户已被冻结");
        } catch (AuthenticationException e) {
            jsonObject.put("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping(value = "/unauth")
    @ResponseBody
    public Object unauth() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未登录");
        return map;
    }



    //登录
    @PostMapping(value = "/login")
    public ModelAndView login (@RequestParam String username,
                               @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView();
        if (username.equals("") && StringUtils.isEmpty(username)) {
            modelAndView.addObject("username", username);
            if (StringUtils.isEmpty(password) && password.equals("")) {
                modelAndView.addObject("password", password);
                modelAndView.setViewName("home");
            }
        }
        return modelAndView;
    }

    //登出
    @GetMapping("hello")
    public ModelAndView hello(ModelMap modelMap){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("hello");
        return new ModelAndView("hello");
    }


    /**
     * TODO 通过id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    @ResponseBody
    public User findById(@PathVariable("id") Long id){
        logger.info(this.getClass().getName() + ".findById(@RequestBody Long id)进入");
        return userService.findById(id);
    }

    @GetMapping("/eureka-instance")
    @ResponseBody
    public String serviceUrl(){
        InstanceInfo instanceInfo = this.eurekaClient.getNextServerFromEureka("SIMPLE-USER", false);
        return instanceInfo.getHomePageUrl();
    }


}
