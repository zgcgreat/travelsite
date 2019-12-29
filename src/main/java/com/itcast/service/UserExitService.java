package com.itcast.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UserExitService
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/7 21:31
 */
@Slf4j
@Service
public class UserExitService {
    public void doBusiness(HttpServletRequest request, HttpServletResponse response)  {
        //1、销毁session
        request.getSession().invalidate();
        try{
            //2、跳转到登录页面
            response.sendRedirect(request.getContextPath() + "/login.html");
        }catch (Exception e){
            log.error("退出异常");
        }

    }
}
