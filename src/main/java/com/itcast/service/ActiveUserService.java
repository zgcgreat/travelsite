package com.itcast.service;

import com.itcast.domain.User;
import com.itcast.mapper.UserMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ActiveUserService
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/5 22:00
 */
@Service
public class ActiveUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * @Author zgc
     * @Description //TODO
     * @Date 2019/10/5 22:02
     * @Param [request, response]
     * @return void
    **/
    public void doBusiness(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");

        if (code != null) {
            //2.调用service完成激活
            boolean flag = active(code);
            //3.判断标记
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    public boolean active(String code) {
        //1.根据激活码查询用户对象
        List<User> user = userMapper.findByCode(code);

        if (user != null && !user.isEmpty()) {
            User u = user.get(0);
            u.setStatus("Y");

            // 激活成功
            userMapper.updateStatus(u);
            return true;
        } else {
            // 激活失败
            return false;
        }
    }
}
