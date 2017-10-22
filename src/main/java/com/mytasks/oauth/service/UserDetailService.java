package com.mytasks.oauth.service;

import com.mytasks.oauth.core.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@Service
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
        return UserDetails.builder().email("test@gmail.com").id("userId").name("test").password("test").role("ADMIN")
                          .build();
    }
}
