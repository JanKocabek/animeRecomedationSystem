package cz.kocabek.animerecomedationsystem.security.config;

import cz.kocabek.animerecomedationsystem.security.service.DbAccountDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    DbAccountDetailService dbAccountDetailService;

    public AuthConfig(DbAccountDetailService dbAccountDetailService) {
        this.dbAccountDetailService = dbAccountDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/main", "/result").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/").loginProcessingUrl("/login")
                        .defaultSuccessUrl("/main", true)
                        .permitAll())
                .logout(LogoutConfigurer::permitAll).build();
    }

    @Bean
    WebSecurityCustomizer ignoreStaticResources() {
        return (web -> web.ignoring().requestMatchers("/assets/**"));
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.builder()
//                        .username("user")
//                        .password("$2a$12$CLIoNT.57SSw9RHlk9y0qe7zS6JYitP3zo81cNjCOUU31Yjtl1516")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
