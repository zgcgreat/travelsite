package com.itcast.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itcast.domain.ResultInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ClassName BaseService
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/7 10:29
 */
@Slf4j
public class BaseService {
    public static void checkCode(HttpServletRequest request, HttpServletResponse response) {
        String check = request.getParameter("check");

        // 从sesion中获取验证码
        HttpSession session          = request.getSession();
        String      checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");

        // 马上清除session，保证验证码只能用一次
        session.removeAttribute("CHECKCODE_SERVER");

        // 比较
        if ((checkcode_server == null) ||!checkcode_server.equalsIgnoreCase(check)) {

            // 验证码错误
            // 注册失败
            ResultInfo info = new ResultInfo();

            info.setFlag(false);
            info.setErrorMsg("验证码错误！请刷新重试······");

            // 将info对象序列化为json
            String json = writeValueAsString(info);

            // 将json数据写回客户端
            // 设置content-type
            response.setContentType("application/json;charset=utf-8");

            try {
                response.getWriter().write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeValue(HttpServletResponse response, Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            response.setContentType("application/json; charset=utf-8");
            mapper.writeValue(response.getOutputStream(), obj);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

    public static String writeValueAsString(Object obj) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error(String.valueOf(e));

            return null;
        }
    }
}
