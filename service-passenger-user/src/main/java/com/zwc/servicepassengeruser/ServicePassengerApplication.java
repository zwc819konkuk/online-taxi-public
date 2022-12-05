package com.zwc.servicepassengeruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zwc.servicepassengeruser.mapper")
public class ServicePassengerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePassengerApplication.class);
    }
}
