package com.hokage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author linyimin
 * @date 2020/8/30 2:51 下午
 * @email linyimin520812@gmail.com
 * @description
 */

// 解决启动时出现BeanNotOfRequiredTypeException问题：https://www.cnblogs.com/threadj/articles/10631193.html
@Configuration
public class ScheduleConfig {
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        scheduler.setThreadNamePrefix("scheduled-task-%d");
        scheduler.initialize();
        return scheduler;
    }
}
