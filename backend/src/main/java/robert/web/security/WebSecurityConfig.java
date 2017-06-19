package robert.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import robert.web.rest.controller.ErrorHandler;
import robert.web.security.auth.filters.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtFilter jwtFilter;

	private final ErrorHandler errorHandler;

	public WebSecurityConfig(JwtFilter jwtFilter, ErrorHandler errorHandler) {
		this.jwtFilter = jwtFilter;
		this.errorHandler = errorHandler;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf()
				.disable()

				.exceptionHandling()
				.authenticationEntryPoint(errorHandler)
				.and()

				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()

				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.ico")
				.permitAll()
				.antMatchers("/auth/**")
				.permitAll()
				.anyRequest()
				.authenticated();

		httpSecurity.headers()
				.cacheControl();

		httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
