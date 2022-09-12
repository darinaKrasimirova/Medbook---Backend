package bg.medbook;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import bg.medbook.model.repository.UserRepository;

@EnableWebSecurity
@Configuration
@SuppressWarnings("deprecation")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JWTFilter filter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(username -> userRepo.findByUsername(username)
		.orElseThrow(() -> new UsernameNotFoundException(String.format("User: %s, not found", username))));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
	return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http = http.cors().and().csrf().disable();

	http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

	http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
	}).and();

	http.authorizeRequests()//
		.antMatchers("/enumeration/**").permitAll()//
		.antMatchers("/workplaces/**").permitAll()//
		.antMatchers("/doctors/**").permitAll()//
		.antMatchers("/users/exist").permitAll()//
		.antMatchers(HttpMethod.POST, "/users/**").permitAll()//
		.anyRequest().authenticated();

	http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}