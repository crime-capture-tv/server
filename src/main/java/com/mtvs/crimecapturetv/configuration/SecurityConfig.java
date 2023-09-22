package com.mtvs.crimecapturetv.configuration;

import com.mtvs.crimecapturetv.configuration.login.UserDetail;
import com.mtvs.crimecapturetv.user.command.service.CommandUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CommandUserService userService;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf().disable() // CSRF 공격에 대한 방어 해제
                .cors().and()
                .authorizeRequests() // URI에 따른 페이지에 대한 권한 부여(antMatchers 기능을 이용하기 위한 메소드)
//                .antMatchers("/api/v1/users/**").permitAll() // 특정 URL 접근 시 인가가 필요한 URI 설정
                .antMatchers("/swagger-ui/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()

                // 폼 로그인 시작
                .formLogin()   // Form Login은 아이디와 비밀번호를 입력해서 들어오는 로그인 형태를 지원하는 Spring Security 기능
                .loginPage("/users/login")  // 커스텀한 로그인 페이지 사용 가능. 생략시 스프링에서 제공하는 페이지로 감
                .failureUrl("/users/login-fail")  // 실패 시 이동 페이지
                .usernameParameter("id") // html에서 "Id"라는 파라미터 이름을 사용 해야 함.
                .passwordParameter("password") // html에서 "password" 라는 파라미터 이름을 사용 해야 함.
                .successHandler(
                        new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                                UserDetail userDetail = (UserDetail) authentication.getPrincipal();
//                                if (!userDetail.isAccountNonExpired()) {
//                                    throw new LockedException("현재 잠긴 계정입니다.");
//                                }

                                HttpSession httpSession = request.getSession();
                                httpSession.setMaxInactiveInterval(3600000);

                                System.out.println("==========principal 값 ===========: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());

//                                response.setContentType("text/html");
//                                PrintWriter out = response.getWriter();
//                                String loginUserName = userService.findUser(authentication.getName()).getId();
//                                out.println("<script>alert('" + loginUserName+ "님 반갑습니다.'); location.href='/';</script>");
//                                out.flush();

                            }
                        }
                )
                .failureHandler(
                        new AuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                String loginFailMessage = "";
                                if (exception instanceof AuthenticationServiceException) {
                                    loginFailMessage = "죄송합니다. 시스템에 오류가 발생했습니다.";
                                } else if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
                                    response.sendRedirect("/users/login?fail");
                                    return;
                                } else if (exception instanceof DisabledException) {
                                    loginFailMessage = "현재 사용할 수 없는 계정입니다.";
                                }
                                response.setContentType("text/html");
                                PrintWriter out = response.getWriter();
                                out.println("<script>alert('" + loginFailMessage + "'); location.href='/users/login';</script>");
                                out.flush();
                            }
                        }
                )
                .permitAll()
                .and()

                // 로그아웃
                .logout()
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(
                        new LogoutSuccessHandler() {
                            @Override
                            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                response.setContentType("text/html");
                                PrintWriter out = response.getWriter();
                                out.println("<script>alert('로그아웃 했습니다.'); location.href='/';</script>");
                                out.flush();
                            }
                        }
                )

                .and()
                .build();
    }


}
