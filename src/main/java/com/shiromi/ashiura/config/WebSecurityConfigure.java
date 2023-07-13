package com.shiromi.ashiura.config;

import com.shiromi.ashiura.config.jwt.JwtAuthenticationFilter;
import com.shiromi.ashiura.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfigure {

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.httpBasic().disable()
                .csrf(csrf -> csrf.disable())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/","/auth/*").permitAll() //권한 유무따지지 않고 모두 접근
                .antMatchers("/view/*").hasRole("N") //해당 권한을 가진 경우
                .antMatchers("/test/*").hasRole("Y")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


//    private final  JwtProvider jwtProvider;
//    private final JwtConfig jwtConfig;


//    @Override
//    public void configure(SecurityBuilder builder) throws Exception {
//        super.configure(builder);
//    }



//        http.httpBasic().disable()
//                .authorizeRequests()
//                .antMatchers("").authenticated()
//                .antMatchers("").hasRole("")
//                .antMatchers("/**").permitAll()
//                .and()
//                .andFilterBefore(new JwtAuthenticationFilter(WebSecurityConfigure),
//                        UsernamePasswordAuthenticationFilter.class);
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
}
