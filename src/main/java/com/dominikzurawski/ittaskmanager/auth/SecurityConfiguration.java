package com.dominikzurawski.ittaskmanager.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // both lines below are just for getting h2-console to work
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // both lines above are just for getting h2-console to work

        // accesing rules and log-in page rules
        http
            .authorizeRequests()
                .antMatchers("/tasks").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/newtask").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/task/edit/*").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/task/delete/*").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/task/assign/*").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/users").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/user/edit/*").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/mytasks").authenticated()
                .antMatchers("/task/toggle/*").authenticated()
                .antMatchers("/user/delete/*").authenticated()
                .antMatchers("/myprofile").authenticated()
                .antMatchers("/myprofile/changepassword").authenticated()
                .antMatchers("/logout").authenticated()
                .antMatchers("/login").anonymous()
                .antMatchers("/register").anonymous()
                .antMatchers("*").permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/?logout");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
