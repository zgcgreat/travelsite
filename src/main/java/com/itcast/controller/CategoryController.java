package com.itcast.controller;

import com.itcast.service.CategoryService;
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
@RequestMapping("/travel/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/findAll")
    @ResponseBody
    public void findCategory(HttpServletRequest request, HttpServletResponse response){
        categoryService.doBusiness(request,response);
    }
}
