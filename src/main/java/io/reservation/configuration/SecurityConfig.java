package io.reservation.configuration;

import io.reservation.web.ApiConst;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static io.reservation.configuration.SecurityConfig.SecurityConst.*;
import static org.springframework.http.HttpMethod.*;

/**
 * Configures application web API security and authentication mechanisms.
 *
 * @author Georgi Velev
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * Configures a {@link JdbcUserDetailsManager} with several test user accounts.
     */
    @Bean
    public UserDetailsService jdbcUserDetailsManager(DataSource dataSource) {
        UserDetails admin = User.builder().passwordEncoder(passwordEncoder()::encode)
                .username("admin").password("admin").roles("ADMIN").build();

        UserDetails user = User.builder().passwordEncoder(passwordEncoder()::encode)
                .username("user").password("user").roles("USER").build();

        UserDetails locked = User.builder().passwordEncoder(passwordEncoder()::encode)
                .username("locked-user").password("user").roles("USER").accountLocked(true).build();

        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.createUser(admin);
        userDetailsManager.createUser(user);
        userDetailsManager.createUser(locked);

        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures security authentication rules role-based access to specific resources
     */
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http = http.csrf().disable();                         //
        http = http.headers().frameOptions().disable().and(); // disabled in order to access H2 web console

        http = http.authorizeRequests()
                // Anyone can access the service about endpoint, browse the API docs and H2 DB console:
                .antMatchers(GET, ApiConst.ABOUT).permitAll()
                .antMatchers(GET, ApiConst.OPEN_API_RESOURCES).permitAll()
                .antMatchers(ApiConst.H2_CONSOLE).permitAll()

                .antMatchers(GET, ApiConst.RESERVATIONS).hasRole(ROLE_ADMIN)
                .antMatchers(POST, ApiConst.RESERVATIONS).hasAnyRole(ROLE_ADMIN, ROLE_USER)

                .antMatchers(GET, ApiConst.RESERVATION_BY_ID).hasAnyRole(ROLE_ADMIN, ROLE_USER)
                .antMatchers(PUT, ApiConst.RESERVATION_BY_ID).hasAnyRole(ROLE_ADMIN, ROLE_USER)
                .antMatchers(DELETE, ApiConst.RESERVATION_BY_ID).hasAnyRole(ROLE_ADMIN)

                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .realmName(RESERVATIONS_REALM)
                .and();

        return http.build();
    }

    /**
     * Holds security related constants like roles, authorities etc.
     */
    public static class SecurityConst {

        public static final String ROLE_ADMIN = "ADMIN";
        public static final String ROLE_USER = "USER";
        public static final String RESERVATIONS_REALM = "reservations";
        private SecurityConst() {
            // pass
        }

    }
}
