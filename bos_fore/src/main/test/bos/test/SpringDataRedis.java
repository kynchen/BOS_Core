package bos.test;/*
* @author kynchen
* 
* @date 2018/8/2 16:23
*
* @version idea
*/

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringDataRedis {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test1(){
        redisTemplate.opsForValue().set("city","杭州",40,TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("city"));
    }
}
