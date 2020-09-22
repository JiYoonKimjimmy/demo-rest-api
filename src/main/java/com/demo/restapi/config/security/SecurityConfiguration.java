package com.demo.restapi.config.security;

import com.demo.restapi.config.security.custom.CustomAccessDeniedHandler;
import com.demo.restapi.config.security.custom.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable()      // rest api 이므로 기본 설정 사용없이, 비인증시 로그인 Form 화면으로 redirect.
            .csrf().disable()           // rest api 이므로 csrf 보안 사용안함.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT token 인증 방식이므로 session 생성 안함.
            .and()
                .authorizeRequests()
                    // 가입 및 인증 api 는 모든 접근 가능
                    .antMatchers("/*/sign/**", "/*/sign/*/**", "/social/**", "/profile").permitAll()
                    // 등록된 GET 요청 리소스는 모든 접근 가능
                    .antMatchers(HttpMethod.GET, "/exception/**", "/favicon.ico").permitAll()
                    // [TEST] user api 는 test 를 위해 admin 권한으로 설정
                    //.antMatchers("/api/user/**").hasRole("ADMIN")
                    // 그외의 요청은 인증된 회원(USER) 인 경우만 접근 가능
                    .anyRequest().hasRole("USER")
            .and()
                // access denied 예외 처리 추가
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
            .and()
                // entryPoint 예외 처리 추가
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and()
                // jwt token filter 를 id/password 인증 필터 앞에 설정
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // swagger security config 무시
        web.ignoring().antMatchers("/v3/api-docs", "/v3/**", "/webjars/**", "/swagger/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**");
    }
}
