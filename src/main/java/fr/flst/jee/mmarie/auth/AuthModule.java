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
 * Created by Maximilien on 11/11/2014.
 */
public class AuthModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public Authenticator<String, User> providesStringUserAuthenticator(AccessTokenService accessTokenService) {
        return new SimpleAuthenticator(accessTokenService);
    }

    @Provides
    @Named("bearer")
    public String providesBearer(BookstoreConfiguration bookstoreConfiguration) {
        return bookstoreConfiguration.getBearerRealm();
    }
}
