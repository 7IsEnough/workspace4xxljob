package com.clearlove.xxljobdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.clearlove.xxljobdemo.mapper")
public class XxlJobDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(XxlJobDemoApplication.class, args);
  }

}
