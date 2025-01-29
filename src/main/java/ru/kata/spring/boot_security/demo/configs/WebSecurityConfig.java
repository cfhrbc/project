package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.servlet.LogbookFilter;
import ru.kata.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final Logbook logbook;

    public WebSecurityConfig(UserService userService, JwtAuthenticationFilter jwtAuthenticationFilter, Logbook logbook) {
        this.userService = userService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.logbook = logbook;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Отключаем CSRF
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Отключаем хранение сессий.
                .and()
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll() // Доступ к /auth/login открыт для всех.
                .antMatchers("/admin/**").hasRole("ADMIN") // Доступ к /admin только для ролей ADMIN.
                .antMatchers("/user/**").hasRole("USER") // Доступ к /user только для ролей USER.
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);// Добавляем наш фильтр перед стандартным фильтром.
        http.addFilterBefore(new LogbookFilter(logbook), JwtAuthenticationFilter.class);

    }

     

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


