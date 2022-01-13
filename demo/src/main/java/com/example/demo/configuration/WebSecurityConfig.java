package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests().antMatchers("/authenticate","/signup").permitAll().
                // all other requests need to be authenticated
                        anyRequest().authenticated().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 허용되어야 할 경로들
        web.ignoring().antMatchers(
                "/",
                "/api/admin", // 관리자 페이지
                "/api/board",// "작성한 뉴스레터 목록",
                "/api/board_detail/{email}", // 관리자 회원 조회
                "/api/signup", // 회원추가
                "/api/group", // "주소록 및 그룹 관리",
                "/api/group/{id}", // 주소록 삭제
                "/api/groupmana", // 그룹 목록
                "/api/groupmana/{group_code}", // 그룹 삭제
                "/api/login",// "로그인",
                "/api/myPage", // 마이페이지
                "/api/getuserall", // 이메일 중복확인
                "/api/edit_profile", // 마이페이지 프로필 수정
                "/api/edit_email_group",
                "/api/edit_email_subscriber",
                "/api/edit_email_board",
                "/api/edit_pw", // 마이페이지 비밀번호 수정
                "/api/createBoard",// "뉴스레터 작성",
                "/api/create-board",// "뉴스레터 작성",
                "/api/selectGroup", // 수신자별 조회 (뉴스레터 작성 페이지 모달창)
                "/api/selected", // 그룹 선택 모달창
                "/api/main",// "원하는 url",
                "/api/hello",// "원하는 url",
                "swagger-ui.html",   // swgger 사용시
                "/index.html",   // front-end 에서 build한 static file
                "/favicon.ico",   // 여기서 설정 안 해주면 index.html이 읽을 수 없음
                "/css/**",   // 여기서 설정 안 해주면 index.html이 읽을 수 없음
                "/fonts/**",   // 여기서 설정 안 해주면 index.html이 읽을 수 없음
                "/img/**",   // 여기서 설정 안 해주면 index.html이 읽을 수 없음
                "/js/**"   // 여기서 설정 안 해주면 index.html이 읽을 수 없음); // #3
        );
    }
}
