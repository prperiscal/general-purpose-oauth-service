package com.general.purpose.oauth.remote.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Represents available roles.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public class Role {

    private String name;
    private int accessLevel;

}
