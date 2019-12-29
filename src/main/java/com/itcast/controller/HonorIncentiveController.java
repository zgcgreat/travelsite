package com.itcast.controller;

import com.itcast.service.DutyStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Joy
 */
@Controller
@RequestMapping("/honor/duty")
public class HonorIncentiveController {
    @Autowired
    private DutyStarService dutyStarService;

    @RequestMapping("/star")
    @ResponseBody
    public void findCategory(HttpServletRequest request, HttpServletResponse response){
        dutyStarService.doBusiness(request,response);
    }
}
