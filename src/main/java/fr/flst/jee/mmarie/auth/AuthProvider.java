package fr.flst.jee.mmarie.auth;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.spi.inject.InjectableProvider;
import fr.flst.jee.mmarie.core.User;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.oauth.OAuthProvider;

/**
 * Created by Maximilien on 11/11/2014.
 */
public class AuthProvider extends OAuthProvider<User> implements InjectableProvider<Auth, Parameter> {
    /**
     * Creates a new OAuthProvider with the given {@link io.dropwizard.auth.Authenticator} and realm.
     *
     * @param authenticator the authenticator which will take the OAuth2 bearer token and convert
     *                      them into instances of {@code T}
     * @param realm         the name of the authentication realm
     */
    @Inject
    public AuthProvider(Authenticator<String, User> authenticator, @Named("bearer") String realm) {
        super(authenticator, realm);
    }
}
