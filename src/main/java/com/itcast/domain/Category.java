package com.itcast.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 分类实体类
 */
@Data
public class Category implements Serializable {

    private int cid;//分类id
    private String cname;//分类名称

    public Category() {
    }

    public Category(int cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

}
