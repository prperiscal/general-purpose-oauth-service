package com.general.purpose.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Bean definition for hashing passwords
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@Configuration
public class HashConfig {

    /**
     * Blowfish password hashing based on: https://www.usenix.org/legacy/event/usenix99/provos/provos.pdf
     * <p>
     * Other encoders mechanisms like MD5PasswordEncoder and ShaPasswordEncoder are supported by Spring Security
     * <p>
     * BCrypt algorithm generates a String of length 60
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
