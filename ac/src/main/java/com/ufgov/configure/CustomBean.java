/**  
 * <p> @Title: CustomBean.java</p>
 * <p> @Package com.ufgov.configure</p>
 * <p> @Description: TODO</p>
 * <p> @Copyright: Copyright 2019 UFGOV</p>
 * <p> @Company: 北京用友政务软件有限公司</p>
 * <p> @author wangpengz  </p>
 * <p> @date 2019年4月29日 下午3:24:00</p>
 * <p> @version V8.5  </p>
 */
package com.ufgov.configure;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @ClassName: CustomBean
 * @Description: 自定义bean
 * @author wangpengz
 * @date 2019年4月29日 下午3:24:00
 */
@Configuration
public class CustomBean {
  @Bean
  public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
      @Override
      public void serialize(Object value, JsonGenerator jg, SerializerProvider sp)
        throws IOException, JsonProcessingException {
        jg.writeString("");
      }
    });
    mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
    return mappingJackson2HttpMessageConverter;
  }
}
