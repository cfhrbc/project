package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.servlet.LogbookFilter;
import javax.servlet.Filter;

@Configuration
public class LogbookConfig {
    @Bean
    public Logbook logbook() {
        return Logbook.builder().build();
    }

    @Bean
    public Filter logbookFilter(Logbook logbook) {
        return new LogbookFilter(logbook);
    }

}
