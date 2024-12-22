package com.example.phonics.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "server")
@Data
public class ServerConfig {
    private String address;

    @Value("${storage.location}")
    private String storageLocation;
    @PostConstruct
    public void init() {
        System.out.println("Server Address: " + address);
    }
}
