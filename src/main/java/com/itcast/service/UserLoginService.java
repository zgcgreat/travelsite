package com.itcast.service;

import com.itcast.domain.ResultInfo;
import com.itcast.domain.User;
import com.itcast.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserLoginService
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/7 10:12
 */
@Slf4j
@Service
public class UserLoginService {

    @Autowired
    private UserMapper userMapper;

    public void doBusiness(HttpServletRequest request, HttpServletResponse response)  {
        //校验激活码
       BaseService.checkCode( request,  response);

        Map<String, String[]> map = request.getParameterMap();

        //封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }

        ResultInfo info = new ResultInfo();
        List<User> userList = login(user);
        //判断查询出来的对象是否为空
        if(userList.isEmpty()){
            //用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }

        if(!userList.isEmpty() && !"Y".equals(userList.get(0).getStatus())){
            //尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请登录邮箱激活！");
        }

        if(!userList.isEmpty() && "Y".equals(userList.get(0).getStatus())){
            //设置session，将来要显示在页面头部的欢迎信息上
            request.getSession().setAttribute("user", userList.get(0));
            //登录成功
            info.setFlag(true);
        }

        //响应数据
        BaseService.writeValue(response, info);

    }

    private List<User> login(User user) {
        return userMapper.findByUsernameAndPassword(user);
    }
}
