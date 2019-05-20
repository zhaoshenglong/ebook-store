package com.ibook.bookstore.security;

import com.ibook.bookstore.Dao.UserDao;
import com.ibook.bookstore.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;


@Configuration
@EnableWebSecurity
public class securityConfiguration extends WebSecurityConfigurerAdapter {
   /*
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
*/

    @Autowired
    private UserDao userDao;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                formLogin()
                    .loginPage("/api/authenticate")
                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                            httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
                            httpServletResponse.setHeader("Access-Control-Allow-Header","*");
                            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
                            httpServletResponse.setStatus(200);
                            HttpSession session = httpServletRequest.getSession();
                            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                            User user = userDao.findOne(userDetails.getUsername());
                            session.setAttribute("name", user.getName());
                            session.setAttribute("email", user.getEmail());
                            session.setAttribute("avatar", user.getAvatar());
                            PrintWriter out = httpServletResponse.getWriter();
                            out.print("{\"msg\":\"success\"}");
                            out.flush();
                            out.close();
                        }
                    })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
                        httpServletResponse.setHeader("Access-Control-Allow-Header","*");
                        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
                        httpServletResponse.setStatus(401);
                        PrintWriter out = httpServletResponse.getWriter();
                        out.print("{\"msg\":\"error\"}");
                        out.flush();
                        out.close();
                    }
                })
                    .permitAll()
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(new LogoutSuccessHandler() {
                        @Override
                        public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                            httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
                            httpServletResponse.setHeader("Access-Control-Allow-Header","*");
                            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
                            PrintWriter out = httpServletResponse.getWriter();
                            httpServletResponse.setStatus(200);
                            out.print(("{\"msg\":\"logout success\"}"));
                            out.flush();
                            out.close();
                        }
                    })
                    .permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers("/api/authenticate", "/logout",
                                "/api/public/**", "/img**", "/login")
                    .permitAll()
                    .antMatchers(HttpMethod.OPTIONS)
                    .permitAll()
                    .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest()
                    .authenticated()
                    .and()
                .csrf()
                    .disable()
                .cors();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8081")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
                        //.exposedHeaders("Access-Control-Allow-Credentials", "Access-Control-Allow-Origin", "Authorization");
            }
        };
    }

}
