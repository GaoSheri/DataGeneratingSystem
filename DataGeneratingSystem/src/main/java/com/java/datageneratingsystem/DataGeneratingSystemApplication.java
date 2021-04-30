package com.java.datageneratingsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("com.java.datageneratingsystem.mapper")
public class DataGeneratingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataGeneratingSystemApplication.class, args);

    }

}
