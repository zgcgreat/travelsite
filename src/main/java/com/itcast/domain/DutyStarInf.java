package com.itcast.domain;

import lombok.Data;

@Data
public class DutyStarInf {
    private String starChgdate;
    private Integer starNum;
    private String starChgRsn;

    public DutyStarInf() {
        super();
    }
}
