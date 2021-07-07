package com.shotcutter.library.config;

import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import com.shotcutter.library.user.User;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@Configuration
public class MessageBrokerConfig {

    @Bean
    @Qualifier(ShotcutterMessageRoutingConstant.User.FIND_BY_ID)
    public Queue findUserByIdQueue() {
        return new Queue(ShotcutterMessageRoutingConstant.User.FIND_BY_ID, false);
    }

    @Bean
    @Qualifier(ShotcutterMessageRoutingConstant.User.FIND_BY_EMAIL)
    public Queue findUserByEmailQueue() {
        return new Queue(ShotcutterMessageRoutingConstant.User.FIND_BY_EMAIL, false);
    }

    @Bean
    @Qualifier(ShotcutterMessageRoutingConstant.User.REGISTRATION)
    public Queue userRegistrationQueue() {
        return new Queue(ShotcutterMessageRoutingConstant.User.REGISTRATION, false);
    }

    @Bean
    @Qualifier(ShotcutterMessageRoutingConstant.Authentication.GET_ID_BY_TOKEN)
    public Queue getIdByTokenQueue() {
        return new Queue(ShotcutterMessageRoutingConstant.Authentication.GET_ID_BY_TOKEN, false);
    }

    @Bean
    public MessageConverter messageConverter() {
        var jsonMessageConverter = new Jackson2JsonMessageConverter();
        jsonMessageConverter.setClassMapper(classMapper());
        return jsonMessageConverter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = Map.ofEntries(
                Map.entry(User.class.getCanonicalName(), User.class)
        );
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }

}
