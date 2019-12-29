package com.itcast.mapper;

import com.itcast.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName UserMapper
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/5 10:22
 */
@Mapper
public interface UserMapper {
    List<User> findByUsername(String username);

    void save(User user);

    List<User> findByCode(String code);

    void updateStatus(User user);

    List<User> findByUsernameAndPassword(User user);
}
