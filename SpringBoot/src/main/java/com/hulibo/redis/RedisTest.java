package com.hulibo.redis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {
	
	private Jedis jedis;
	
	@Before
	public void connect()
	{
		jedis=new Jedis("192.168.0.154",6379);
		String code=jedis.auth("hulibo");
		System.out.println("连接服务器"+code);
	}

	@Test
	public void addString()
	{
		jedis.set("key1","www.sina.com");
		System.out.println(jedis.get("key1"));
	}
	
	@Test
	public void addList()
	{
		jedis.lpush("mylist","a");
		jedis.lpush("mylist","b");
		jedis.lpush("mylist","c");
		jedis.lpush("mylist","d");
		jedis.lpush("mylist","e");
		jedis.lpush("mylist","f");
		jedis.lpush("mylist","g");
		
		List<String> list=jedis.lrange("mylist",0,-1);
		for (String data : list) {
			System.out.println(data);
		}
	}
	
	@Test
	public void keys()
	{
		Set<String> keys=jedis.keys("*");
		System.out.println(jedis.configGet("requirepass"));
		Iterator<String> it=keys.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
	}
}
