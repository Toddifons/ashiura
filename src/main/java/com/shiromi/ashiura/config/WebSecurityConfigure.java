package com.shiromi.ashiura.config;

import com.shiromi.ashiura.config.jwt.JwtAuthenticationFilter;
import com.shiromi.ashiura.config.jwt.JwtProvider;
import com.shiromi.ashiura.handler.WebAccessDeniedHandler;
import com.shiromi.ashiura.handler.WebAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfigure {

    private final JwtProvider jwtProvider;
    private final WebAccessDeniedHandler webAccessDeniedHandler;
    private final WebAuthenticationEntryPoint webAuthenticationEntryPoint;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.httpBasic().disable()
                .csrf(csrf -> csrf.disable())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/view/**","/test/**","api/**").hasRole("N") //해당 권한을 가진 경우
//                .antMatchers().hasRole("Y")
                .antMatchers("/view/**").hasRole("USER")
//                .antMatchers("/","/err/**","/auth/**","/justwait","/img/**","test/**").permitAll() //권한 유무따지지 않고 모두 접근
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
//                .formLogin()
//                .loginPage("auth/loginPage")
//                .loginProcessingUrl("auth/loginForm")
//                .defaultSuccessUrl("/")
//                .and()
                .exceptionHandling()
                .accessDeniedHandler(webAccessDeniedHandler)
                .authenticationEntryPoint(webAuthenticationEntryPoint)
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .clearAuthentication(true)
                .deleteCookies("Bearer")
                .logoutSuccessUrl("/auth/loginPage")
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}