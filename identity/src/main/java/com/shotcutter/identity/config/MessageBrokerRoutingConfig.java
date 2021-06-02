package com.shotcutter.identity.config;

import com.shotcutter.library.messaging.ShotcutterMessageRoutingConstant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;

@Configuration
public class MessageBrokerRoutingConfig {

    @Bean
    DirectExchange userExchange() {
        return new DirectExchange(ShotcutterMessageRoutingConstant.User.EXCHANGE_NAME);
    }

    @Bean
    Binding findByEmailBinding(@Qualifier(ShotcutterMessageRoutingConstant.User.FIND_BY_EMAIL) Queue userQueue) {
        return BindingBuilder
                .bind(userQueue)
                .to(userExchange())
                .with(ShotcutterMessageRoutingConstant.User.FIND_BY_EMAIL);
    }

    @Bean
    Binding findByIdBinding(@Qualifier(ShotcutterMessageRoutingConstant.User.FIND_BY_ID) Queue userQueue) {
        return BindingBuilder
                .bind(userQueue)
                .to(userExchange())
                .with(ShotcutterMessageRoutingConstant.User.FIND_BY_ID);
    }

    @Bean
    Binding registrationBinding(
            @Qualifier(ShotcutterMessageRoutingConstant.User.REGISTRATION) Queue registrationQueue) {
        return BindingBuilder
                .bind(registrationQueue)
                .to(userExchange())
                .with(ShotcutterMessageRoutingConstant.User.REGISTRATION);
    }

}
