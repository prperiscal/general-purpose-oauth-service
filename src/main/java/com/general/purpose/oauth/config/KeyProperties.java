package com.general.purpose.oauth.config;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>For configuring key properties.
 *
 * @since 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties("general.purpose.oauth.token")
public class KeyProperties {

    /**
     * <p>.jks file name
     */
    @NotEmpty
    private String keyFileName;

    /**
     * <p>.jks name
     */
    @NotEmpty
    private String keyPair;

    /**
     * <p>Key password
     */
    @NotEmpty
    private String keyPassword;

}