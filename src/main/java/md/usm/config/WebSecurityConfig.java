package md.usm.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import md.usm.repo.UserRepo;
import md.usm.utils.InitialDataGenerator;

import static md.usm.utils.Util.extractUserDetailsForUser;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepo userRepo;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/register", "/categories", "/products/**", "/images/**", "/css/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        userRepo.saveAll(InitialDataGenerator.createUsers());
        final List<md.usm.model.user.User> users = (List<md.usm.model.user.User>) userRepo.findAll();
        return new InMemoryUserDetailsManager(extractUserDetailsForUser(users.get(0)), extractUserDetailsForUser(users.get(1)));
    }
}