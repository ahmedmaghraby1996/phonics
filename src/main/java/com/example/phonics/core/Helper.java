package com.example.phonics.core;

import com.example.phonics.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    // Static instance of ServerConfig

    @Autowired
    private  ServerConfig serverConfig;



    // Static method to generate the URL
    public  String toUrl(String path) {



        return serverConfig.getAddress()+":"+serverConfig.getPort()+"/"+ path;
    }
}
