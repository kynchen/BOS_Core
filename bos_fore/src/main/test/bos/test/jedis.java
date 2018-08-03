package bos.test;/*
* @author kynchen
* 
* @date 2018/8/2 16:03
*
* @version idea
*/

import redis.clients.jedis.Jedis;

public class jedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        jedis.setex("username",50,"kyn");
        System.out.println(jedis.get("username"));
    }
}
