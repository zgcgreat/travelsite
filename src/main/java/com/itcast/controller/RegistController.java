package com.itcast.controller;

import com.itcast.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName RegistController
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/3 14:51
 */

@Controller
@RequestMapping("/travel/user")
public class RegistController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private CheckCodeService checkCodeService;

    @Autowired
    private ActiveUserService activeUserService;

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    private FindUserService findUserService;

    @Autowired
    private UserExitService userExitService;

    @RequestMapping("/registUser")
    @ResponseBody
    public void registUser(HttpServletRequest request, HttpServletResponse response){
        HttpServletResponse response1 = iUserService.doBusiness(request, response);
    }

    @RequestMapping("/checkCode")
    @ResponseBody
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkCodeService.doPost(request, response);
    }

    @RequestMapping("/activeUser")
    @ResponseBody
    public void activeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        activeUserService.doBusiness(request, response);
    }

    @RequestMapping("/login")
    @ResponseBody
    public void userLogin(HttpServletRequest request, HttpServletResponse response) {
        userLoginService.doBusiness(request, response);
    }

    @RequestMapping("/findUser")
    @ResponseBody
    public void findUser(HttpServletRequest request, HttpServletResponse response){
        findUserService.doBusiness(request, response);
    }

    @RequestMapping("/exit")
    @ResponseBody
    public void userExit(HttpServletRequest request, HttpServletResponse response){
        userExitService.doBusiness(request, response);
    }

}

