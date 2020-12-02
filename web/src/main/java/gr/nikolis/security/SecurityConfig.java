package gr.nikolis.security;

import gr.nikolis.handlers.LoggingAccessDeniedHandler;
import gr.nikolis.sql.constants.Roles;
import gr.nikolis.mappings.home.HomeMappings;
import gr.nikolis.mappings.login.LoginMappings;
import gr.nikolis.mappings.login.ViewNames;
import gr.nikolis.mappings.register.RegisterMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String [] permitted = {
            HomeMappings.HOME_DEFAULT,
            HomeMappings.HOME,
            RegisterMappings.REGISTER,
            RegisterMappings.CONFIRM,
            LoginMappings.USER_DISABLED,
            "/customers/**",
            "/js/**",
            "/css/**",
            "/img/**",
            "/webjars/**"
    };

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired //This gets the instance that has been created from @Bean or @Component or @Service (In other words creates the reference)
    private LoggingAccessDeniedHandler loggingAccessDeniedHandler;

    @Autowired
    private AuthenticationFailureHandler loggingAuthenticationHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //disable crf in order to do POST on webservices
                .cors().and().csrf().disable()

                .authorizeRequests()
                .antMatchers(permitted).permitAll()
                .antMatchers(String.format("%s/**", LoginMappings.USER)).hasRole(Roles.ADMIN_ROLE) //the page user can only be accessed those who have role admin
                .antMatchers(String.format("%s/**", LoginMappings.USER)).hasAuthority(Roles.ADMIN_ROLE) //the page user can only be accessed those who have authority admin
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage(LoginMappings.LOGIN)
                    //.failureHandler(loggingAuthenticationHandler)
                    .permitAll()
                .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher(LoginMappings.LOGOUT))
                    .logoutSuccessUrl(String.format("%s?%s",LoginMappings.LOGIN, ViewNames.LOGOUT))
                    .permitAll()
                .and()
                        .exceptionHandling()
                        .accessDeniedHandler(loggingAccessDeniedHandler);
    }

    @Override
    public void configure(WebSecurity web) {
    }
}
