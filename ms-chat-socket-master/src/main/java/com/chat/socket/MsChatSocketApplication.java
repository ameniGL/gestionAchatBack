package com.chat.socket;

import com.chat.socket.configuration.WebSocketConfiguration;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication(scanBasePackages = {"com.alibou.security.config", "com.alibou.security.user", "com.alibou.security.demo"})

public class MsChatSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsChatSocketApplication.class, args);
    }

}
