package org.spring.security2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigureClass implements WebMvcConfigurer {

    String saveFiles="file:///E:/fullSaveFiles/security/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/upload/***") //실제경로는 saveFiles 인데 화면에 뿌릴때는 이렇게 뿌림
                .addResourceLocations(saveFiles);




    }


}
