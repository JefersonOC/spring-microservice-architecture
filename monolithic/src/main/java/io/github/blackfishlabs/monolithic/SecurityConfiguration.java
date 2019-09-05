package io.github.blackfishlabs.monolithic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("barry").password("t0ps3cr3t").roles("USER").build());
        manager.createUser(users.username("larry").password("t0ps3cr3t").roles("USER", "MANAGER").build());
        manager.createUser(users.username("root").password("t0ps3cr3t").roles("USER", "MANAGER", "ADMIN").build());
        return manager;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**").permitAll()
                .antMatchers("/students/**").hasAnyRole("USER")
                .antMatchers("/subjects/**").hasAnyRole("MANAGER")
                .anyRequest().fullyAuthenticated()
                .and().httpBasic().and().csrf().disable();
    }
}
