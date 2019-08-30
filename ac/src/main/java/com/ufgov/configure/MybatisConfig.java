package com.ufgov.configure;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ufgov.ip.MyBatisDao;


/**
 * 
 * @author wp
 *
 */
@Configuration
public class MybatisConfig {
  @Bean
  public MapperScannerConfigurer mapperScannerConfigurer() throws ClassNotFoundException {
    MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
    mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
    mapperScannerConfigurer.setBasePackage("com.ufgov");
    mapperScannerConfigurer.setAnnotationClass(MyBatisDao.class);
    return mapperScannerConfigurer;
  }
}
