package com.general.purpose.oauth.client.exception;

/**
 * <p>Generic exception for defining errors while retrieve intercepting and setting
 * the client token within the feign call.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
public class GenericFeignInterceptorException extends RuntimeException {

    private static final String MESSAGE = "Some went wrong while intercepting and setting the client token within the feign call";

    /**
     * Constructs a new Generic Feign Interceptor exception with the specified cause and a
     * detail message. This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     *
     * @since 1.0.0
     */
    public GenericFeignInterceptorException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
