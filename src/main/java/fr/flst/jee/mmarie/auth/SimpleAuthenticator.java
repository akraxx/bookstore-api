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
 * Created by Maximilien on 07/11/2014.
 */
@Slf4j
public class SimpleAuthenticator implements Authenticator<String, User> {
    public static final int ACCESS_TOKEN_EXPIRE_TIME_MIN = 1440;
    private AccessTokenService accessTokenService;

    public SimpleAuthenticator(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    @Override
    public Optional<User> authenticate(String accessTokenId) throws AuthenticationException {
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
