package robert.web.security.filters;

import org.apache.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import robert.exeptions.UserAuthException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BasicAuthFilter implements Filter {

	protected static final Logger log = Logger.getLogger(BasicAuthFilter.class);

	protected static final AntPathMatcher matcher = new AntPathMatcher();

	@Override
	public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		try {
			doAuth(request, response);
			filterChain.doFilter(servletRequest, response);
		} catch (UserAuthException ignored) {
			log.error("Auth failed on uri: " + request.getRequestURI());
			response.sendRedirect("/not-logged-in.html");
		} catch (Throwable ignored) {
			response.sendRedirect("/not-logged-in.html");
		}
	}

	abstract void doAuth(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
