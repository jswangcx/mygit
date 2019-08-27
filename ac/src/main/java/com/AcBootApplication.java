package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author wp
 *
 */
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy=true)
@EnableTransactionManagement
public class AcBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(AcBootApplication.class, args);
  }

}
