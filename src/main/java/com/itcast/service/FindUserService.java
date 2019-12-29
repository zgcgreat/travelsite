package com.itcast.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName FindUserService
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/7 21:03
 */
@Slf4j
@Service
public class FindUserService {
    public void doBusiness(HttpServletRequest request, HttpServletResponse response)  {
        Object user = request.getSession().getAttribute("user");
        BaseService.writeValue(response, user);
    }
}
