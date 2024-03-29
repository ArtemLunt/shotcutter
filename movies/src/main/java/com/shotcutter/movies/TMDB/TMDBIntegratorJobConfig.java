package com.shotcutter.movies.TMDB;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;

@Configuration
@ConditionalOnProperty(value = "tmdb.integration.init")
public class TMDBIntegratorJobConfig {
    @Bean(name = "tmdb-integration")
    TaskExecutor getTaskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(1);
        executor.setCorePoolSize(1);
        executor.setQueueCapacity(1);
        executor.setThreadNamePrefix("shotcutter-");
        return executor;
    }
}
