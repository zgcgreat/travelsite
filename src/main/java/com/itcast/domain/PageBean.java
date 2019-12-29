package com.itcast.domain;

import lombok.Data;

import java.util.List;

/**
 * @ClassName PageBean
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/20 20:50
 */
@Data
public class PageBean<T> {
    /**
     * 总记录数
    **/
    private int totalCount;
    /**
     * 总页数
     **/
    private int totalPage;
    /**
     * 当前页
     **/
    private int currentPage;
    /**
     * 每页显示条数
     **/
    private int pageSize;
    /**
     * 每页显示的数据集合
     **/
    private List<T> list;
}
