package cz.muni.fi.pa165.rest.config;

import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.rest.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static cz.muni.fi.pa165.rest.model.Role.ADMIN;
import static cz.muni.fi.pa165.rest.model.Role.USER;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @author juraj
 */
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtTokenUtil;
    private final UserFacade userFacade;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
        throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        if (!jwtTokenUtil.isExpired(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        UserDetails userDetails = userFacade
            .findByName(jwtTokenUtil.getUsername(token))
            .map(user ->
                new User(user.getName(), user.getPasswordHash(),
                    user.isAdmin() ? List.of(new Role(ADMIN), new Role(USER)) : List.of(new Role(USER)))
            ).orElse(null);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                jwtTokenUtil.getUserId(token), userDetails,
                userDetails == null ?
                    List.of() : userDetails.getAuthorities()
            );

        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}