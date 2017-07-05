package robert.web.security;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import robert.web.rest.controllers.handlers.AuthEntryPoint;
import robert.web.security.auth.filters.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf()
				.disable()

				.exceptionHandling()
				.authenticationEntryPoint(errorHandler())
				.and()

				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()

				.headers()
				.cacheControl();

		httpSecurity.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.ico").permitAll()
				.antMatchers("/auth/**").permitAll()
				.anyRequest().authenticated();

		httpSecurity.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public AuthenticationEntryPoint errorHandler() {
		return new AuthEntryPoint();
	}

	@Bean
	public Filter jwtFilter() {
		return new JwtFilter();
	}

}
