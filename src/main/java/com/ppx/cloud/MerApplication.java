package com.ppx.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ppx.cloud.common.util.ApplicationUtils;

@SpringBootApplication
public class MerApplication {

    public static void main(String[] args) {
        ApplicationUtils.context = SpringApplication.run(MerApplication.class, args);
    }

   
}