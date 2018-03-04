package com.general.purpose.oauth.service;

import com.general.purpose.oauth.core.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service to retrieve user identities containing authentication info.
 * <p>
 * Check for more information on how we handle users {@link org.springframework.security.core.userdetails.UserDetails UserDetails}
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@Service
public class UserDetailService implements UserDetailsService {

    /**
     * Method used to determinate which user is logged in.
     *
     * @param userEmail String user identifier. In our case this field will be the user email
     *
     * @return UserDetails {@link UserDetails}. Out implementation for {@link org.springframework.security.core.userdetails.UserDetails Spring user Details}
     * @throws UsernameNotFoundException if user was no found
     */
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return UserDetails.builder().email("test@gmail.com").name("test").password("test").role("ADMIN").build();
    }
}
