package com.itcast.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itcast.domain.ResultInfo;
import com.itcast.domain.User;
import com.itcast.mapper.UserMapper;
import com.itcast.util.MailUtils;
import com.itcast.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @Author:
 * @Version: V1.0
 * @Date: 2018/12/25 9:32
 * @Description: TODO
 **/
@Service
@Slf4j
public class IUserService {

    @Autowired
    private UserMapper userMapper;

    public HttpServletResponse doBusiness(HttpServletRequest request, HttpServletResponse response) {

        String check = request.getParameter("check");

        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //马上清除session，保证验证码只能用一次
        session.removeAttribute("CHECKCODE_SERVER");
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            // 验证码错误
            // 注册失败
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！请刷新重试······");
            try {
                //将info对象序列化为json
                String json = writeValueAsString(info);
                //将json数据写回客户端
                //设置content-type
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(json);
            } catch (Exception e) {
                log.info("用户注册异常");
            }
            return response;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用service完成注册
        boolean flag = regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if (flag) {
            // 注册成功
            info.setFlag(true);
        } else {
            // 注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败！！！");
        }

        try {
            //将info对象序列化为json
            String json = writeValueAsString(info);
            //将json数据写回客户端
            //设置content-type
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            log.info("用户注册异常");
        }
        return response;

    }


    private boolean regist(User user) {
        //1.根据用户名查询用户对象
        List<User> u = userMapper.findByUsername(user.getUsername());
        //判断u是否为null
        if (u != null && !u.isEmpty()) {
            //用户名存在，注册失败
            return false;
        }

        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        userMapper.save(user);
        //3.激活邮件发送，邮件正文？
        String content = "<a href='http://localhost:8080/travel/user/activeUser?code=" + user.getCode() + "'>点击激活黑马旅游网</a>";

        MailUtils.sendMail(user.getEmail(), content, "激活邮件");

        return true;
    }


    private String writeValueAsString(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
