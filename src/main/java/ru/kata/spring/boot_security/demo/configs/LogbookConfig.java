package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.HeaderFilters;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.QueryFilters;
import org.zalando.logbook.servlet.LogbookFilter;

import javax.servlet.Filter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Configuration
public class LogbookConfig {
    @Bean
    public Logbook logbook() {
        return Logbook.builder()
                .queryFilter(QueryFilters.replaceQuery("auth/login", "***MASKED***")) // Скрываем URL
                .headerFilter(HeaderFilters.replaceHeaders("Authorization", "***MASKED***")) // Маскируем токен в заголовке
                .bodyFilter(BodyFilter.merge(
                        maskSensitiveData("password"),
                        maskSensitiveData("token")
                )) // Маскируем password и token в теле запроса/ответа
                .build();
    }



    @Bean
    public Filter logbookFilter(Logbook logbook) {
        return new LogbookFilter(logbook);
    }

    private boolean isExcludedEndpoint(String uri) {
        return uri.contains("/auth/login");  // Отключаем логирование для /auth/login
    }

    private BodyFilter maskSensitiveData(String field) {
        String regex = "(\"" + field + "\"\\s*:\\s*\").*?(\")"; // Находим JSON-поле и его значение
        Pattern pattern = Pattern.compile(regex);

        return (contentType, body) -> pattern.matcher(body).replaceAll("$1***MASKED***$2");
    }
}
