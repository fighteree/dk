package com.yzdbx.dkpan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yzdbx.dkpan.dao.mysql")
public class DkPanApplication {

    public static void main(String[] args) {
        SpringApplication.run(DkPanApplication.class, args);
        System.out.println("hello");
        System.out.println("world");
    }

}
