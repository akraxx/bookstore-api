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
 * <p>
 *     {@link fr.flst.jee.mmarie.auth.AuthProvider} is an {@link io.dropwizard.auth.oauth.OAuthProvider} which is automatically injected
 *     by the {@link com.hubspot.dropwizard.guice.GuiceBundle} in the {@link fr.flst.jee.mmarie.BookstoreApplication}.
 * </p>
 *
 * <p>
 *     Thanks to this {@link com.sun.jersey.spi.inject.InjectableProvider}, we can protect our jersey resource with the
 *     {@link io.dropwizard.auth.Auth} annotation.
 * </p>
 *
 * @see <a href="http://dropwizard.io/manual/auth.html">Dropwizard authentication documentation</a>
 */
public class AuthProvider extends OAuthProvider<User> implements InjectableProvider<Auth, Parameter> {
    /**
     * Creates a new OAuthProvider with the given {@link io.dropwizard.auth.Authenticator} and realm.
     *
     * @param authenticator the authenticator which will take the OAuth2 bearer token and convert
     *                      them into instances of {@code User}
     * @param realm         the name of the authentication realm
     */
    @Inject
    public AuthProvider(Authenticator<String, User> authenticator, @Named("bearer") String realm) {
        super(authenticator, realm);
    }
}
