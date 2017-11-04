package be.kdg.blog.config;

import be.kdg.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * deze klasse bevat vooral deel 3 van week 6
 */

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public ApplicationSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    //deel 1 van week 6
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/blog").authenticated() //welke urls moeten geauthenticeerd worden (voor welke urls moeten men ingelogt zijn)
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login").permitAll() //deze url is toeganklijk voor iedereen & is ook de auto-redirect als men naar vb. "/blog" gaat en niet ingelogt is
                .and()
                .logout()
                .permitAll();
    }

    //DEEL 3
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //deze config methode wordt gebruikt om eventuele password encoders in te stellen
        auth.authenticationProvider(this.authProvider());
    }

    //DEEL 3
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.userService);
        authProvider.setPasswordEncoder(this.passwordEncoder());
        return authProvider;
    }

    //DEEL 3
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
