package com.mytasks.oauth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
public class UserDetailService implements UserDetailsService {

    /**
     * Method used to determinate which user is logged in.
     *
     * @param userEmail String user identifier. In our case this field will be the user email
     *
     * @return UserDetails
     * @throws UsernameNotFoundException if user was no found
     */
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return null;
    }
}
