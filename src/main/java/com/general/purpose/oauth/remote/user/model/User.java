package com.general.purpose.oauth.remote.user.model;

import java.util.UUID;

import lombok.Data;

/**
 * <p>A {@link User} resource containing the base attributes.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Data
public class User {

    private UUID id;
    private UUID tenantId;
    private String email;
    private String name;
    private String role;
    private String password;

}
