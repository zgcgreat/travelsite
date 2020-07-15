package com.itcast.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Joy
 * @version 1.0
 * @desc
 * @date 2020/7/15 21:06
 */
@Component
@Data
public class JedisProperties {
    @Value("${host}")
    private String host;
}
