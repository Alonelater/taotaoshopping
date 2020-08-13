package com.taotao.sso.dao;

/**
 *
 * 由于未来我们不知道是使用集群redis还是单机redis  所以我们准备一个接口  这也是今天学习的依赖倒转原则
 */
public interface JedisClient {

    //制定规范
    //获取key对应的值
    String get(String key);
    //设置key
    String set(String key, String value);
    //获取key对应的值
    String hget(String hkey, String key);
    //设置key 和值
    long hset(String hkey, String key, String value);
    //将key加1
    long incr(String key);
    //设置有效时间
    long expire(String key, int second);
    //查看key失效时间
    long ttl(String key);
    //删除key  就是更新已经改动过的
    long del(String key);
    //删除hset里面的key
    long hdel(String hkey, String key);
}
