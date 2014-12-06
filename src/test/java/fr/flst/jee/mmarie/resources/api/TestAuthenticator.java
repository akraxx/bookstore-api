package fr.flst.jee.mmarie.resources.api;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

/**
 * Created by Maximilien on 06/12/2014.
 */
public class TestAuthenticator implements Authenticator<String, User> {
    @Override
    public Optional<User> authenticate(String credentials) throws AuthenticationException {
        if(credentials.equals("good-token")) {
            return Optional.of(User.builder().login("test").email("test@test.com").password("test").build());
        } else {
            return Optional.absent();
        }
    }
}
