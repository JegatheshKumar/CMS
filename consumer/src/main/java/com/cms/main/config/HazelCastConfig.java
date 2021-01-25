package com.cms.main.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelCastConfig {

	@Autowired
	HazelcastInstance instance;

	@Bean
	public Config configuration() {

		Config config = new Config();
		config.setInstanceName("hazelcast-instance");
		MapConfig mapConfig = new MapConfig();
		mapConfig.setName("messages");
		mapConfig.setTimeToLiveSeconds(-1);
		config.addMapConfig(mapConfig);
		return config;
	}

	@Bean
	public Map<Object, Object> HazelCastMessagesMap() {
		return instance.getMap("messages");
	}
}
