package com.itcast.controller;

import com.itcast.service.CategoryService;
import com.itcast.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CategoryController
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/9 21:55
 */
@Controller
@RequestMapping("/travel/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @RequestMapping("/pageQuery")
    @ResponseBody
    /**
     * @Author zgc
     * @Description 分页查询
     * @Date 2019/10/20 20:57
     * @Param [request, response]
     * @return void
    **/
    public void pageQuery(HttpServletRequest request, HttpServletResponse response){
        routeService.doBusiness(request,response);
    }
}
