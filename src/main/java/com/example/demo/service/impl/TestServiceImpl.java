package com.example.demo.service.impl;

import cn.hutool.json.JSONObject;
import com.example.demo.dao.TestDao;
import com.example.demo.datasource.JedisClusterSourceConfig;
import com.example.demo.entity.Test;
import com.example.demo.service.TestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @author luzy
 * @date 20/11/12 17:00
 */
@Service
@Transactional
public class TestServiceImpl implements TestService {

    @Resource
    TestDao testDao;

    public static final int KEEP_USER_STAY_TIME_SECOND = 60*60;

    @Override
    public String getUserId(String id_one, String id_two) {
        String result;
        JedisCluster jedisCluster = JedisClusterSourceConfig.getJedisClusterConnections();
        String key = String.format("test:%s:%s",id_one,id_two);
        result = jedisCluster.get(key);
        if(result!=null && "".equals(result)){
            return result;
        }
        Test test = testDao.getUserId(id_one,id_two);
        if(test!=null && "".equals(test)){
            result = new JSONObject(test,true).toString();
            jedisCluster.set(key,result);
            jedisCluster.expire(key,KEEP_USER_STAY_TIME_SECOND);
            return result;
        }
        return "{}";
    }
}
