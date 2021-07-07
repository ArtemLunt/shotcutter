package com.shotcutter.sso.config;

import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageBrokerRoutingConfig {

    @Bean
    public DirectExchange authExchange() {
        return new DirectExchange(ShotcutterMessageRoutingConstant.Authentication.EXCHANGE_NAME);
    }

    @Bean
    Binding validateUserBinding(@Qualifier(ShotcutterMessageRoutingConstant.Authentication.GET_ID_BY_TOKEN) Queue authQueue) {
        return BindingBuilder
                .bind(authQueue)
                .to(authExchange())
                .with(ShotcutterMessageRoutingConstant.Authentication.GET_ID_BY_TOKEN);
    }

}
