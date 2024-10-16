package com.hanghae.project.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = "com.hanghae.project")
@EnableJpaRepositories(basePackages = "com.hanghae.project")
@EntityScan(basePackages = "com.hanghae.project")
public class WebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebapiApplication.class, args);
    }

}
