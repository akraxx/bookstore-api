package fr.flst.jee.mmarie.resources.api;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import lombok.Getter;

/**
 * Created by Maximilien on 06/12/2014.
 */
public class TestAuthenticator implements Authenticator<String, User> {

    @Getter
    private User authenticatedUser = User.builder().login("test").email("test@test.com").password("test").build();

    @Override
    public Optional<User> authenticate(String credentials) throws AuthenticationException {
        if(credentials.equals("good-token")) {
            return Optional.of(authenticatedUser);
        } else {
            return Optional.absent();
        }
    }
}
