package com.example.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${store.root_path}")
    String storePath;
    @Value("${store.access_dir}")
    String accessDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //registry.addResourceHandler("/advertIMG/**").addResourceLocations("file:D:/rentHouse/");
        registry.addResourceHandler("/"+accessDir+"/**").addResourceLocations("file:"+storePath);
    }
 
}