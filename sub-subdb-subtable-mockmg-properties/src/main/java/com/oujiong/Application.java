package com.oujiong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: 启动类
 *
 * @author xub
 * @date 2019/10/08 下午6:33
 */
@SpringBootApplication
@MapperScan(basePackages = "com.oujiong.mapper")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
