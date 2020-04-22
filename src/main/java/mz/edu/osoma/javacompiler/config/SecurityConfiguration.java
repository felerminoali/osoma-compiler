package mz.edu.osoma.javacompiler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/execute", "/").permitAll()
                .antMatchers("/js/**").permitAll() // permit JS resources
                .antMatchers("/fonts/**").permitAll() // permit fonts resources
                .antMatchers("/images/**").permitAll() // permit images resources
                .antMatchers("/vendor/**").permitAll() // permit JS resources
                .antMatchers("/css/**").permitAll() // permit CSS resources
                .antMatchers("/assets/**").permitAll();
    }


}
