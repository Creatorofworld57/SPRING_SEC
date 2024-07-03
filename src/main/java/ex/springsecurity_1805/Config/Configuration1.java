package ex.springsecurity_1805.Config;


import ex.springsecurity_1805.Repositories.UserRepository;
import ex.springsecurity_1805.services.MyUserDetailsService;
import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class Configuration1{
    UserRepository repository;
    @Bean
    public UserDetailsService userDetailsService(){
       /* UserDetails admin0 = User.builder().username("admin0").password(encoder.encode("52")).roles("ADMIN").build();
        UserDetails admin1 = User.builder().username("admin1").password(encoder.encode("703.747")).roles("USER").build();
        UserDetails admin2 = User.builder().username("admin2").password(encoder.encode("18")).roles("USER").build();*/
     return new MyUserDetailsService(repository);
    }
    @Bean
    static public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth.requestMatchers("api/Welcome","/api/login","/api/user").permitAll()
                                .requestMatchers("/api/newUser").anonymous()
                .requestMatchers("/api/**").authenticated()
                        )
                 //.requestMatchers(HttpMethod.GET).authenticated())
                   .formLogin(formLogin ->
                                formLogin
                                        .loginPage("/api/login")
                                        .loginProcessingUrl("/perform_login")
                                        .defaultSuccessUrl("/api/Welcome")
                                        .failureUrl("/api/login")
                                        .passwordParameter("password")
                                        .usernameParameter("name"))
                 .logout(logout->logout
                 .logoutUrl("/api/logout")
                 .logoutSuccessUrl("/api/login")
                 .permitAll());

        //.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }




}
