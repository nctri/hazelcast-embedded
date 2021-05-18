package com.example.hazelcast.config;

import com.example.hazelcast.model.Employee;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.SerializerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelCastConfig() {

        Config config = new Config();
        config.setInstanceName("hazelcast-cache");

        MapConfig allUsersCache = new MapConfig();
        allUsersCache.setTimeToLiveSeconds(20);
        allUsersCache.setEvictionConfig(new EvictionConfig()
                .setEvictionPolicy(EvictionPolicy.LFU)
                .setMaxSizePolicy(MaxSizePolicy.PER_NODE)
        );
        config.getMapConfigs().put("allEmployeeCache",allUsersCache);

        MapConfig userCache = new MapConfig();
        userCache.setTimeToLiveSeconds(20);
        userCache.setEvictionConfig(new EvictionConfig()
                .setEvictionPolicy(EvictionPolicy.LFU)
                .setMaxSizePolicy(MaxSizePolicy.PER_NODE)
        );
        config.getMapConfigs().put("employeeCache", userCache);

        config.getSerializationConfig().addSerializerConfig(serializerConfig());

        return config;
    }

    public SerializerConfig serializerConfig() {
        return new SerializerConfig()
                .setImplementation(new EmployeeStreamSerializer())
                .setTypeClass(Employee.class);
    }

}