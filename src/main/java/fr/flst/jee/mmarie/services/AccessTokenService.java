package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Singleton;
import fr.flst.jee.mmarie.core.AccessToken;
import fr.flst.jee.mmarie.core.User;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Maximilien on 07/11/2014.
 */
@Singleton
@Slf4j
public class AccessTokenService {
    private static Map<UUID, AccessToken> accessTokens = new HashMap<>();

    public Optional<AccessToken> findAccessTokenById(final UUID accessTokenId) {
        AccessToken accessToken = accessTokens.get(accessTokenId);
        if (accessToken == null) {
            return Optional.absent();
        }
        return Optional.of(accessToken);
    }

    public AccessToken generateNewAccessToken(final User user, final DateTime dateTime) {
        AccessToken accessToken = new AccessToken(UUID.randomUUID(), user, dateTime);
        accessTokens.put(accessToken.getAccessTokenId(), accessToken);
        return accessToken;
    }

    public void setLastAccessTime(final UUID accessTokenUUID, final DateTime dateTime) {
        AccessToken accessToken = accessTokens.get(accessTokenUUID);
        AccessToken updatedAccessToken = accessToken.withLastAccessUTC(dateTime);
        accessTokens.put(accessTokenUUID, updatedAccessToken);
    }

    public void updatedUser(User user) {
        for (AccessToken accessToken : accessTokens.values()) {
            if(accessToken.getUser().getLogin().equals(user.getLogin())) {
                accessToken.setUser(user);
            }
        }
    }
}
