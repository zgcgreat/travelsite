package com.itcast.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Joy
 * @version 1.0
 * @desc
 * @date 2020/7/15 21:34
 */
@Data
public class JedisConfig {
    private String host;

    public JedisConfig(JedisProperties jedisProperties){
        this.host = jedisProperties.getHost();
    }
}
