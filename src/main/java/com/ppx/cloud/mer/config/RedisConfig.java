package com.ppx.cloud.mer.config;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppx.cloud.common.util.MD5Utils;


/**
 * 配置redis成只当作缓存使用 maxmemory:128M maxmemory:allkeys-lru save:""
 * @author mark
 * @date 2018年6月22日
 */
//@Configuration
public class RedisConfig {
    
    // 为了让FirstConfig.runFirstConf先运行 (@ComponentScan自动扫描之后@Order不生效)
    @Resource(name = "runFirstConf")
    private Object runFirstConf;
	
	public static final String WISELY_GENERATOR = "WISELY_GENERATOR";

	/**
	 * 复杂参数缓存key生成，包括class, method, params
	 * 如果需要分组，@Bean(name=WISELY_GENERATOR_STORE_ID), return storeId + "::" + MD5Utils.getMD5(sb.toString());
	 * @return
	 */
	@Bean(name=WISELY_GENERATOR)
	public KeyGenerator wiselyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
			    
				StringBuilder sb = new StringBuilder(target.getClass().getSimpleName());
				sb.append(method.getName());
				    
				ObjectMapper om = new ObjectMapper();
				om.setSerializationInclusion(Include.NON_NULL);
				String key = "";
				try {
					key = om.writeValueAsString(params);
					sb.append(key);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				
				return  MD5Utils.getMD5(sb.toString());
			}
		};
	}
	
	@Bean
	@Primary
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
	    
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
	
		RedisCacheConfiguration c = config.serializeValuesWith(SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
		// redis配置
		RedisConnection connection = connectionFactory.getConnection();
		connection.setConfig("maxmemory", "128M");
		connection.setConfig("maxmemory-policy", "allkeys-lru");
		connection.setConfig("save", "");
		
		CacheManager cm = RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory).cacheDefaults(c).build();
		return cm;
	}

}
