package fr.flst.jee.mmarie.auth;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import fr.flst.jee.mmarie.BookstoreConfiguration;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.services.AccessTokenService;
import io.dropwizard.auth.Authenticator;

/**
 * {@link fr.flst.jee.mmarie.auth.AuthModule}, tools for the OAuth authentication used in the whole application.
 */
public class AuthModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    /**
     * Provides a {@link com.google.inject.Singleton} of {@link io.dropwizard.auth.Authenticator}. The implementation used is the
     * {@link fr.flst.jee.mmarie.auth.SimpleAuthenticator}.
     *
     * @param accessTokenService Injected instance of {@link fr.flst.jee.mmarie.services.AccessTokenService}
     * @return A {@link fr.flst.jee.mmarie.auth.SimpleAuthenticator}
     */
    @Provides
    @Singleton
    public Authenticator<String, User> providesStringUserAuthenticator(AccessTokenService accessTokenService) {
        return new SimpleAuthenticator(accessTokenService);
    }

    /**
     * Provides the bearer realm of the application.
     *
     * @param bookstoreConfiguration {@link fr.flst.jee.mmarie.BookstoreConfiguration}
     * @return The realm defined in the configuration.
     */
    @Provides
    @Named("bearer")
    public String providesBearer(BookstoreConfiguration bookstoreConfiguration) {
        return bookstoreConfiguration.getBearerRealm();
    }
}
