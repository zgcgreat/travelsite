package com.itcast.service;

import com.itcast.domain.PageBean;
import com.itcast.domain.Route;
import com.itcast.mapper.RouteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RouteService
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/20 20:58
 */
@Slf4j
@Service
public class RouteService {

    @Autowired
    RouteMapper routeMapper;

    public void doBusiness(HttpServletRequest request, HttpServletResponse response) {
        //1.接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //接收rname 路线名称
        String rname = request.getParameter("rname");
//        try {
//            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            log.error(String.valueOf(e));
//        }

        //2.处理参数
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }

        //当前页码
        int currentPage;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        //每页显示记录数
        int pageSize;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }

        int start = (currentPage - 1) * pageSize;
        Map<String, Object> paramMap = buildParamMap(cid, start, pageSize, rname);
        int totalCount = routeMapper.findTotalCount(paramMap);
        List<Route> routeList = routeMapper.findByPage(paramMap);

        PageBean<Route> pb = genResult(currentPage, pageSize, totalCount, routeList);

        //响应数据
        BaseService.writeValue(response, pb);
    }

    private Map<String, Object> buildParamMap(int cid, int start, int pageSize, String rname) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("cid", cid);
        paramMap.put("start", start);
        paramMap.put("pageSize", pageSize);
        paramMap.put("rname", rname);
        return paramMap;
    }

    private PageBean<Route> genResult(int currentPage, int pageSize, int totalCount, List<Route> routeList) {
        PageBean<Route> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        pb.setTotalCount(totalCount);
        pb.setList(routeList);
        //总页数=总记录数 / 每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }
}
