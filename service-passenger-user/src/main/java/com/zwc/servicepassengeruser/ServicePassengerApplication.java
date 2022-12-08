package com.zwc.servicepassengeruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.zwc.servicepassengeruser.mapper")
@EnableDiscoveryClient
public class ServicePassengerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePassengerApplication.class);
    }
}
