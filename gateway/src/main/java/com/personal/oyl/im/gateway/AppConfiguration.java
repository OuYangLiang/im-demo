package com.personal.oyl.im.gateway;

import com.personal.oyl.im.gateway.model.MessageMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author OuYang Liang
 */
@Configuration
public class AppConfiguration {

    @Bean
    public MessageMapper eventMapper(SqlSessionFactory sqlSessionFactory) throws Exception {
        MapperFactoryBean<MessageMapper> factory = new MapperFactoryBean<>();
        factory.setSqlSessionFactory(sqlSessionFactory);
        factory.setMapperInterface(MessageMapper.class);
        return factory.getObject();
    }

}
