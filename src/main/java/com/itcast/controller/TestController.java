package com.itcast.controller;

import com.itcast.util.JedisConfig;
import com.itcast.util.JedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Joy
 * @version 1.0
 * @desc
 * @date 2020/7/15 21:27
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    JedisProperties jedisProperties;

    @RequestMapping("/hello")
    @ResponseBody
    /**
     * @Author zgc
     * @Description 分页查询
     * @Date 2019/10/20 20:57
     * @Param [request, response]
     * @return void
     **/
    public void test(HttpServletRequest request, HttpServletResponse response){
        JedisConfig jedisConfig = new JedisConfig(jedisProperties);
        String host = jedisConfig.getHost();
        System.out.println(host);
    }
}
