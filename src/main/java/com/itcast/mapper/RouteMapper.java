package com.itcast.mapper;

import com.itcast.domain.Route;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName RouteMapper
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/20 21:05
 */
@Mapper
public interface RouteMapper {
    /**
     * 根据cid查询总记录数
     **/
    int findTotalCount(Map<String, Object> paramMap);

    /**
     * 根据cid,start,pageSize查询当前页的数据集合
     **/
    List<Route> findByPage(Map<String, Object> paramMap); //int cid, int start, int pageSize
}
