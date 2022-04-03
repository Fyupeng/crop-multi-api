package com.crop.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                //.addResourceLocations("file:D:/tony_videos_dev/");
                .addResourceLocations("file:" + System.getProperties().getProperty("user.home") + "/"
                + "webapps" + "/" + "crop_multi_data/");
    }


}
