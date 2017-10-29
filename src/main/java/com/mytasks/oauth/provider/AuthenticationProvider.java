package com.mytasks.oauth.provider;

import static java.util.Collections.emptyList;

import com.mytasks.oauth.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Implementation of a custom Authentication Provider for providing User authentication check.
 * <p>
 * Every user (not client) will trigger {@link #authenticate(Authentication)} where the verification will be handle.
 * <p>
 * {@link UserDetailService} will be used to retrieve the user.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@RequiredArgsConstructor
@Configuration
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;

    /**
     * Checks user credentials.
     *
     * @param authentication the authentication request object.
     *
     * @return fully {@link Authentication} object including credentials.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //The user name in authentication process will allways be the email in our implementation
        String userEmail = authentication.getName();
        String pass = authentication.getCredentials().toString();
        String rawPassword = (String) authentication.getCredentials();
        if(!passwordEncoder.matches(rawPassword, pass)) {
            //throw new BadCredentialsException("Wrong password");
        }

        return new UsernamePasswordAuthenticationToken(userEmail, pass, emptyList());
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     *
     * @param authentication Authentication class
     *
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
