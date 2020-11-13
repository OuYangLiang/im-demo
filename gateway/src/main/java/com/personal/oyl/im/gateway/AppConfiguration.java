package com.personal.oyl.im.gateway;

import com.personal.oyl.im.gateway.im.GroupMessageMapper;
import com.personal.oyl.im.gateway.im.MessageMapper;
import com.personal.oyl.im.gateway.user.UserMapper;
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
    public MessageMapper messageMapper(SqlSessionFactory sqlSessionFactory) throws Exception {
        MapperFactoryBean<MessageMapper> factory = new MapperFactoryBean<>();
        factory.setSqlSessionFactory(sqlSessionFactory);
        factory.setMapperInterface(MessageMapper.class);
        return factory.getObject();
    }

    @Bean
    public GroupMessageMapper groupMessageMapper(SqlSessionFactory sqlSessionFactory) throws Exception {
        MapperFactoryBean<GroupMessageMapper> factory = new MapperFactoryBean<>();
        factory.setSqlSessionFactory(sqlSessionFactory);
        factory.setMapperInterface(GroupMessageMapper.class);
        return factory.getObject();
    }

    @Bean
    public UserMapper userMapper(SqlSessionFactory sqlSessionFactory) throws Exception {
        MapperFactoryBean<UserMapper> factory = new MapperFactoryBean<>();
        factory.setSqlSessionFactory(sqlSessionFactory);
        factory.setMapperInterface(UserMapper.class);
        return factory.getObject();
    }

}
