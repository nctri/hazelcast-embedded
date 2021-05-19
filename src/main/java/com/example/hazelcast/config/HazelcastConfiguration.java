package com.example.hazelcast.config;

import com.example.hazelcast.model.Employee;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.SerializerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelCastConfig() {

        Config config = new Config();
        config.setClusterName("dev");
        config.setInstanceName("hazelcast-cache");
        config.setManagementCenterConfig(managementCenterConfig());

        config.getCPSubsystemConfig().setCPMemberCount(3);

        NetworkConfig network = config.getNetworkConfig();
        network.setPort(5701).setPortCount(20);
        network.setPortAutoIncrement(true);
        JoinConfig join = network.getJoin();
        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig()
                .addMember("localhost")
                .addMember("127.0.0.1").setEnabled(true);

        MapConfig allUsersCache = new MapConfig();
        allUsersCache.setName("allEmployeeCache");
        allUsersCache.setTimeToLiveSeconds(20);
        allUsersCache.setEvictionConfig(new EvictionConfig()
                .setEvictionPolicy(EvictionPolicy.LFU)
                .setMaxSizePolicy(MaxSizePolicy.PER_NODE)
        );
        config.getMapConfigs().put("allEmployeeCache",allUsersCache);
/*
        MapConfig userCache = new MapConfig();
        userCache.setTimeToLiveSeconds(20);
        userCache.setEvictionConfig(new EvictionConfig()
                .setEvictionPolicy(EvictionPolicy.LFU)
                .setMaxSizePolicy(MaxSizePolicy.PER_NODE)
        );
        config.getMapConfigs().put("employeeCache", userCache);*/

        config.getSerializationConfig().addSerializerConfig(serializerConfig());

        return config;
    }

    private ManagementCenterConfig managementCenterConfig() {
        return new ManagementCenterConfig().addTrustedInterface("127.0.0.1")
                .setScriptingEnabled(true);
    }

    public SerializerConfig serializerConfig() {
        return new SerializerConfig()
                .setImplementation(new EmployeeStreamSerializer())
                .setTypeClass(Employee.class);
    }

}