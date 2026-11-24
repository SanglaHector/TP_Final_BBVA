package org.example.catalogodecamisetasv2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncExecutorConfig {

    @Bean(name = "workflowExecutor")
    public ExecutorService workflowExecutor() {
        // Pool de 5 hilos
        return Executors.newFixedThreadPool(5);
    }
}

