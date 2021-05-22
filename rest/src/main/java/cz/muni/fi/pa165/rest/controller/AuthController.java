package cz.muni.fi.pa165.rest.controller;

import cz.muni.fi.pa165.dto.UserDto;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.rest.model.AuthRequest;
import cz.muni.fi.pa165.rest.model.TokenAuthResponse;
import cz.muni.fi.pa165.rest.config.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

import static cz.muni.fi.pa165.rest.model.Role.USER;

/**
 * @author juraj
 */

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserFacade userFacade;
    private final JwtUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                    )
                );

            UserDto user = userFacade.findByName(request.getUsername()).orElseThrow(() -> new BadCredentialsException("user doesnt exist"));
            String token = jwtTokenUtil.generateAccessToken(user);
            return ResponseEntity.ok(new TokenAuthResponse(token));
        } catch (BadCredentialsException ex) {
            log.warn("bad creds");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("asdasd");
        }
    }

    @GetMapping("/refresh")
    @RolesAllowed(USER)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        final String token = authToken.substring("Bearer ".length());
        String username = jwtTokenUtil.getUsername(token);

        Optional<UserDto> user = userFacade.findByName(username);
        if (user.isPresent()) {
            String refreshedToken = jwtTokenUtil.generateAccessToken(user.get());

            return ResponseEntity.ok(new TokenAuthResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}