package jtechlog.springsecurity.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password("$2a$10$ADR5Ol7to6gUl4zdL1iasu4Oa/J9La4r30Jjbgaq0X946HvsWqTT2")
//                .authorities("USER");

//        auth
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(
//                "select username, password, 1 from users " +
//                        "where username = ?")
//                .authoritiesByUsernameQuery(
//                        "select username, role from users " +
//                                "where username = ?");

        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/")
                    .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .and()
                .formLogin()
                .loginPage("/login")
                .failureHandler(usernameInUrlAuthenticationFailureHandler())
                .and()
                .logout();
    }

    @Bean
    public UsernameInUrlAuthenticationFailureHandler usernameInUrlAuthenticationFailureHandler() {
        return new UsernameInUrlAuthenticationFailureHandler();
    }
}
