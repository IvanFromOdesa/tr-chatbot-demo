package dev.ivank.trchatbotdemo.common.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import java.util.concurrent.*;

/**
 * Configuration for asynchronous 'task executors'
 */
@Configuration
@EnableAsync
public class BaseAsyncConfig implements AsyncConfigurer {

    @Bean(name = "fixedThreadPool")
    public Executor baseExecutorService() {
        return Executors.newFixedThreadPool(8);
    }

    private ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncExecutor-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "baseThreadPoolExecutor")
    public ExecutorService baseThreadPoolExecutor() {
        return new ThreadPoolExecutor(10, 50, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
    }

    @Bean(name = "delegatingSecurityContextExecutor")
    public Executor delegatingSecurityContextExecutor() {
        return new DelegatingSecurityContextAsyncTaskExecutor(threadPoolTaskExecutor());
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncPropagatedExceptionHandler();
    }

    @Override
    public Executor getAsyncExecutor() {
        return baseExecutorService();
    }
}
