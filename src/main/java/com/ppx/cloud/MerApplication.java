package com.ppx.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;

import com.ppx.cloud.common.util.ApplicationUtils;

@SpringBootApplication
public class MerApplication {

    public static void main(String[] args) {
        ApplicationUtils.context = SpringApplication.run(MerApplication.class, args);
        ApplicationHome home = new ApplicationHome(MerApplication.class);
        ApplicationUtils.JAR_HOME = home.getSource().getParent() + "/";
    }

   
}