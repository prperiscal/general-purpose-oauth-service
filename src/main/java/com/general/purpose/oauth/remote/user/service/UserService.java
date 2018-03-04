package com.general.purpose.oauth.remote.user.service;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Set;

import com.general.purpose.oauth.remote.user.model.User;
import feign.FeignException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>User service to retrieve data form user-service.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class UserService {
    private static final String USER_SERVICE = "user-service";
    private static final String PROJECTION_NAME = "UserBaseWithGroupsAndPassword";
    private static final String FIND_BY_EMAIL_PATH = "/api/users/search";
    private static final String FIND_ONE_PATH = "api/tenants/{tenantId}/users/{userId}";

    @NonNull
    private final UserServiceProxy userServiceProxy;

    /**
     * <p>Retrieves {@link User User/s} with the given email.
     *
     * @param email user email
     *
     * @return {@link Set<User>} with the users
     * @throws FeignException if something fails
     * @since 1.0.0
     */
    public Set<User> findByEmail(String email) {
        return userServiceProxy.findByEmail(email, PROJECTION_NAME);
    }


    /**
     * <p>Feign client to communicates with user serivce
     *
     * @since 1.0.0
     */
    @FeignClient(name = USER_SERVICE)
    private interface UserServiceProxy {

        /**
         * <p>Retrieves {@link User User/s} with the given email.
         *
         * @param email          user email
         * @param projectionName the name of the projection the {@link User} shall be converted to
         *
         * @return {@link Set<User>} with the users
         * @throws FeignException if something fails
         * @since 1.0.0
         */
        @RequestMapping(method = GET, path = FIND_BY_EMAIL_PATH, produces = APPLICATION_JSON_UTF8_VALUE)
        Set<User> findByEmail(@RequestParam("email") String email, @RequestParam("projection") String projectionName);

    }
}
