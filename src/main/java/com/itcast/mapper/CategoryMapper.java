package com.itcast.mapper;

import com.itcast.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName CategoryMapper
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/9 21:45
 */
@Mapper
public interface CategoryMapper {
    List<Category> findAll();
}
