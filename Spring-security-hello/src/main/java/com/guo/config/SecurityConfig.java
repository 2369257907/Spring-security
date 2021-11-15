package com.guo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置
 *
 * @author guoheng
 * @date 2021/11/10
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在内存中自定义对象，使用了BCryptPasswordEncoder加密
        auth
                .inMemoryAuthentication()
                .withUser("foo")
                .password(passwordEncoder.encode("12345"))
                .roles("admin");

//                .and()
//                .withUser("bar")
//                .password("12345")
//                .roles("admin");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("name")
                .passwordParameter("pwd")
                .defaultSuccessUrl("/index.html",true)
//                .successForwardUrl("/index.html")
                .permitAll()


                //设置退出页面，解决二次登录的问题
                .and()
                .logout()
                .logoutSuccessUrl("/")

                //默认设置
//                .logoutUrl("/logout")
//                .deleteCookies()  //指定删除cookie的名称
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)


                .and()
                .csrf().disable();
    }
}
