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
        return http.httpBasic().disable() //스프링 시큐리티 기본 페이지 비활성
                .csrf(csrf -> csrf.disable()) //CSRF를 이용한 공격 방지, post put method에 적용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/view/**").hasAnyRole("ADMIN","USER") // "/view/**" 아래에 "ADMIN","USER"의 권한중 둘중 하나라도 가졌다면 승인
                .antMatchers("/**").permitAll() //모든 사용자 모든 URI 승인
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(webAccessDeniedHandler) //403에 대한 핸들링
                .authenticationEntryPoint(webAuthenticationEntryPoint) //401에 대한 핸들링
                .and()
                .logout()
                .logoutUrl("/auth/logout") //로그아웃이 실행될 주소
                .clearAuthentication(true) //인증정보 휘발
                .deleteCookies("accessToken","refreshToken") //해당 이름의 쿠키를 삭제
                .logoutSuccessUrl("/auth/loginPage") // 로그아웃 후 돌아올 장소
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class) //JWT를 필터에 적용
                .build();
    }
}