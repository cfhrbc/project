package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.HeaderFilters;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.QueryFilters;
import org.zalando.logbook.servlet.LogbookFilter;

import javax.servlet.Filter;
import java.util.regex.Pattern;

@Configuration
public class LogbookConfig {

    @Bean
    public Logbook logbook() {
        return Logbook.builder()
                .queryFilter(QueryFilters.replaceQuery("auth/login", "***MASKED***"))
                .headerFilter(HeaderFilters.replaceHeaders("Authorization", "***MASKED***"))
                .bodyFilter(BodyFilter.merge(
                        maskSensitiveData("password"),
                        maskSensitiveData("token")
                ))
                .build();
    }

    @Bean
    public Filter logbookFilter(Logbook logbook) {
        return new LogbookFilter(logbook);
    }

    private BodyFilter maskSensitiveData(String field) {
        var regex = "(\"" + field + "\"\\s*:\\s*\").*?(\")";
        Pattern pattern = Pattern.compile(regex);

        return (contentType, body) -> pattern.matcher(body).replaceAll("$1***MASKED***$2");
    }
}
