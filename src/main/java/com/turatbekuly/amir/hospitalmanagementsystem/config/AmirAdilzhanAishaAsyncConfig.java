package com.turatbekuly.amir.hospitalmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AmirAdilzhanAishaAsyncConfig {

    @Bean(name = "AmirAdilzhanAishaTaskExecutor")
    public Executor AmirAdilzhanAishaTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(6);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("amir-adilzhan-aisha-async-");
        executor.initialize();
        return executor;
    }
}
