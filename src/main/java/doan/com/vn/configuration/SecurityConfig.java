package doan.com.vn.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import doan.com.vn.service.auth.CustomUserDetailService;
import doan.com.vn.utils.LoginSuccessHandler;
import doan.com.vn.utils.RoleName;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    public PasswordEncoder paswordEncoder;
    
    @Autowired 
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http.authorizeHttpRequests((authorize) -> authorize
                .antMatchers("/", "/lien-he", "/thoi-khoa-bieu", "/tin-tuc-1",
                        "/tin-tuc-2", "/tin-tuc-3", "/chi-tiet-tuyen-sinh")
                .permitAll().antMatchers("/ad/**", "/us/**", "images/**").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority(RoleName.ADMIN.name(), RoleName.TEACHER.name())
                .antMatchers("/user/**").hasAnyAuthority(RoleName.ADMIN.name(), RoleName.TEACHER.name(), RoleName.STUDENT.name())
                .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .loginProcessingUrl("/login")
//                        .successHandler(loginSuccessHandler)
                        .defaultSuccessUrl("/admin", true)
                        .permitAll())
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        return http.build();
    }

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(paswordEncoder);

//        auth.inMemoryAuthentication()
//        .withUser("giangtt6")
//        .password(paswordEncoder.encode("123"))
//        .roles("USER");

    }

}
