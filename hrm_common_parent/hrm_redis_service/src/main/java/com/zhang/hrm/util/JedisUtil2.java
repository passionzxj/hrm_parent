package com.zhang.hrm.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil2 {
    //Redis服务器IP
    private static String ADDR = "localhost";

    //Redis的端口号
    private static int PORT = 6379;

    //访问密码
    private static String AUTH = "123456";

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    //超时时间
    private static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    //链接池名
    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            System.out.println(" 初始化redis链接池:开始");
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);//高版本的jedis中使用
//            config.setMaxActive(MAX_ACTIVE);//低版本的jedis中使用
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);//高版本的jedis中使用
//            config.setMaxWait(MAX_WAIT);//低版本的jedis中使用
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            System.out.println(" 初始化redis链接池:成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" 初始化redis链接池:失败，请停止web服务排除故障后重新运行，错误原因："+e.getMessage());
        }
    }
    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis fetch() throws Exception{
        return jedisPool.getResource();
    }

    /**
     * 释放Jedis资源
     * @param jedis
     */
    public static void free(final Jedis jedis) {
        if(jedisPool==null||jedis==null) return;
        jedisPool.returnResource(jedis);
    }
}
