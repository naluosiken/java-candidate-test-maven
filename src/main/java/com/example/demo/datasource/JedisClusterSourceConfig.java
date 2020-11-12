package com.example.demo.datasource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.*;

/**
 * 集群redis配置
 */
public class JedisClusterSourceConfig {

    private static JedisCluster jedisCluster=null;
    private static Properties props = new Properties();

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            props.load(JedisClusterSourceConfig.class.getResourceAsStream("/jedisClusterConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        //Jedis池配置
        JedisPoolConfig config =new JedisPoolConfig();
        //最大连接数, 默认8个
        config.setMaxTotal(500);
        //最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(8);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(1000 * 10);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        jedisCluster = new JedisCluster(
                JedisClusterSourceConfig.getJedisClusterHostAndPort(),
                2000,
                2000,
                100,
                JedisClusterSourceConfig.getKeyValue("iot.redis.cluster.password"),
                config);
    }

    public static JedisCluster getJedisClusterConnections(){
        return jedisCluster;
    }

    public static List<String> getMatchesProperties(String pattern) {
        List<String> list = new ArrayList<String>();
        Set<String> keySet = props.stringPropertyNames();
        for (String key : keySet) {
            if (key.matches(pattern)) {
                list.add(props.getProperty(key));
            }
        }
        Collections.sort(list);
        return list;
    }

    public static Set<HostAndPort> getJedisClusterHostAndPort(){
        Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();

        List<String> links = getMatchesProperties("iot.redis.cluster.node.\\d+");
        for (String link : links) {
            String[] tokens = link.split(":");

            hostAndPorts.add(new HostAndPort(tokens[0], Integer.parseInt(tokens[1])));
        }
        return hostAndPorts;
    }



    /**
     * 读取属性文件中相应键的值
     * @param key
     *            主键
     * @return String
     */
    public static String getKeyValue(String key) {
        return props.getProperty(key);
    }
}
