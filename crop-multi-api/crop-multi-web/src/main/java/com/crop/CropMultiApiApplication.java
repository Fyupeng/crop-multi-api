package com.crop;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Auther: fyp
 * @Date: 2022/2/23
 * @Description:
 * @Package: com.crop
 * @Version: 1.0
 */


@SpringBootApplication
@MapperScan(basePackages = "com.crop.mapper")
@ComponentScan(basePackages = {"com.crop", "org.n3r.idworker"})
public class CropMultiApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CropMultiApiApplication.class, args);
    }
}


