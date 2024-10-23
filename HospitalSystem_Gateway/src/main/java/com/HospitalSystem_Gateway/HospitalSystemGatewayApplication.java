package com.HospitalSystem_Gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class HospitalSystemGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalSystemGatewayApplication.class, args);
    }
}
