

import javafx.application.Application;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;

public class TestJedis {


    @Test
    public  void testJedisSingle(){
        //创建jedis连接对象
        Jedis jedis = new Jedis("192.168.1.120",6379);
        //设置值
        jedis.set("key1","jedis test");
        String key1 = jedis.get("key1");
        System.out.println(key1);
        //对象关闭
        jedis.close();

    }


    /**
     *
     * 使用连接池
     *
     */
    @Test
    public void testJedisPool(){
        //创建连接池
        JedisPool jedisPool = new JedisPool("192.168.1.120", 6379);
        //从连接池获得jedis对象
        Jedis jedis = jedisPool.getResource();
        String key1 = jedis.get("key1");
        System.out.println(key1);
        //归还连接池
        jedis.close();
        //关闭连接池
        jedisPool.close();

    }

    @Test
    public void testJedisCluster() throws IOException {

        //一定要记得关闭防火墙

        //创建连接池
        HashSet<HostAndPort> node = new HashSet<>();
        node.add(new HostAndPort("192.168.1.120",7001));
        node.add(new HostAndPort("192.168.1.120",7002));
        node.add(new HostAndPort("192.168.1.120",7003));
        node.add(new HostAndPort("192.168.1.120",7004));
        node.add(new HostAndPort("192.168.1.120",7005));
        node.add(new HostAndPort("192.168.1.120",7006));

        JedisCluster jedisCluster = new JedisCluster(node);
        jedisCluster.set("key1","1100");
        String key1 = jedisCluster.get("k");
        String key2 = jedisCluster.get("hello");

        System.out.println(key1);
        System.out.println(key2);
        jedisCluster.close();
    }


    /**
     *
     * 使用连接池
     *
     */
    @Test
    public void testSpringJedisPool(){
        //获得spring帮我们创建的连接池
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/applicationContext-*.xml");
        JedisPool jedisPool = (JedisPool) ioc.getBean("redisClient");


        //从连接池获得jedis对象
        Jedis jedis = jedisPool.getResource();
        String key1 = jedis.get("key1");
        System.out.println(key1);
        //归还连接池
        jedis.close();
        //关闭连接池
        jedisPool.close();

    }


    @Test
    public void testSpringJedisCluster() throws IOException {

        //一定要记得关闭防火墙

        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/applicationContext-*.xml");
        JedisCluster jedisCluster= (JedisCluster) ioc.getBean("redisClientCluster");


        jedisCluster.set("key1","1100");
        String key1 = jedisCluster.get("k");
        String key2 = jedisCluster.get("hello");

        System.out.println(key1);
        System.out.println(key2);
        jedisCluster.close();
    }

}
