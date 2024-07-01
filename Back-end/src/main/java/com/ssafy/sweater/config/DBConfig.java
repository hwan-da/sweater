package com.ssafy.sweater.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.ssafy.sweater.model.dao")
public class DBConfig {

}
