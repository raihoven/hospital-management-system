package com.turatbekuly.amir.hospitalmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class TuratbekulyAmirAsyncConfig {

    @Bean(name = "turatbekulyAmirTaskExecutor")
    public Executor turatbekulyAmirTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(6);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("turatbekuly-amir-async-");
        executor.initialize();
        return executor;
    }
}
