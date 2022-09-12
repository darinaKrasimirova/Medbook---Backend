package bg.medbook;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import bg.medbook.model.entity.User;
import bg.medbook.model.repository.UserRepository;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	String authHeader = request.getHeader("Authorization");
	if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
	    String jwt = authHeader.substring(7);
	    if (jwt == null || jwt.isBlank()) {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
	    } else {
		try {
		    String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
		    UserDetails userDetails = getService().loadUserByUsername(username);
		    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
			    userDetails.getPassword(), userDetails.getAuthorities());
		    if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(authToken);
		    }
		} catch (JWTVerificationException exc) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
		    return;
		}
	    }
	}

	filterChain.doFilter(request, response);
    }

    private UserDetailsService getService() {
	return new UserDetailsService() {
	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findOneByUsername(username);
		if (user == null)
		    throw new UsernameNotFoundException("");

		return user;
	    }
	};
    }
}