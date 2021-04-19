package com.marc.fmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.marc.fmall.mapper")
public class FmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmallApplication.class, args);
    }

}
