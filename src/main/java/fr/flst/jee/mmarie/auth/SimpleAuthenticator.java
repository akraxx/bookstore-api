package fr.flst.jee.mmarie.auth;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.AccessToken;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.services.AccessTokenService;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.UUID;

/**
 * <p>
 *     A simple implementation of {@link io.dropwizard.auth.Authenticator}.
 * </p>
 *
 * <p>
 *     Here we will use the {@link fr.flst.jee.mmarie.services.AccessTokenService} to authenticate our
 *     {@link fr.flst.jee.mmarie.core.User}.
 * </p>
 *
 * <p>
 *     Example of http request used by this {@link fr.flst.jee.mmarie.auth.SimpleAuthenticator} is :
 *     <b>Authorization: Bearer 6e4f2d67-04d0-4d6d-8b69-0eaa4d08c993</b>
 * </p>
 */
@Slf4j
public class SimpleAuthenticator implements Authenticator<String, User> {
    public static final int ACCESS_TOKEN_EXPIRE_TIME_MIN = 30;
    private AccessTokenService accessTokenService;

    public SimpleAuthenticator(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    /**
     * <p>
     *     Authenticate the given {@code accessTokenId}. Search in the {@link fr.flst.jee.mmarie.services.AccessTokenService} if
     *     the given {@code accessTokenId} is registered and has not expired.
     * </p>
     *
     * @param accessTokenId token given by the user in the http header request
     * @return The user if he has been successfully logged.
     */
    @Override
    public Optional<User> authenticate(String accessTokenId) {
        log.info("Authenticate user with token {}", accessTokenId);
        // Check input, must be a valid UUID
        UUID accessTokenUUID;
        try {
            accessTokenUUID = UUID.fromString(accessTokenId);
        } catch (IllegalArgumentException e) {
            return Optional.absent();
        }

        // Get the access token from the database
        Optional<AccessToken> accessToken = accessTokenService.findAccessTokenById(accessTokenUUID);
        if (accessToken == null || !accessToken.isPresent()) {
            return Optional.absent();
        }

        // Check if the last access time is not too far in the past (the access token is expired)
        Period period = new Period(accessToken.get().getLastAccessUTC(), new DateTime());
        if (period.getMinutes() > ACCESS_TOKEN_EXPIRE_TIME_MIN) {
            return Optional.absent();
        }

        // Update the access time for the token
        accessTokenService.setLastAccessTime(accessTokenUUID, new DateTime());

        // Return the user's id for processing
        return Optional.of(accessToken.get().getUser());
    }
}
