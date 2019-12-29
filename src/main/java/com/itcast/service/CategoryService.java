package com.itcast.service;

import com.itcast.domain.Category;
import com.itcast.mapper.CategoryMapper;
import com.itcast.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName CategoryService
 * @Description TODO
 * @Author zgc
 * @Date 2019/10/9 21:50
 */
@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public void doBusiness(HttpServletRequest request, HttpServletResponse response)  {

//        List<Category> categoryList = findCategory();
        List<Category> categoryList = categoryMapper.findAll();
        //响应数据
        BaseService.writeValue(response, categoryList);
    }

    private List<Category> findCategory(){
        //1、从Redis中查询
        //1.1 获取Redis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2 使用sortedset排序查询
//        Set<String> categorys = jedis.zrange("category", 0, -1);
        //1.3 查询sortedset中的分数（cid）和值（cname）
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs;
        //2. 判断查询的集合是否为空
        if(categorys == null || categorys.isEmpty()){
            System.out.println("从数据库查询");
            //3. 如果为空，需从数据库查询，再将数据存入redis
            //3.1 从数据库查询
            cs = categoryMapper.findAll();
            //3.2 将集合数据存储到redis中的category的key
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        }else {
            System.out.println("从redis中查询");
            //4. 如果不为空，将set的数据存入list
            cs = new ArrayList<>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                cs.add(category);
            }
        }
        return cs;
    }
}
