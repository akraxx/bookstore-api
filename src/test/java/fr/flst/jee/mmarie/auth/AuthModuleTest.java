package fr.flst.jee.mmarie.auth;

import fr.flst.jee.mmarie.BookstoreConfiguration;
import fr.flst.jee.mmarie.core.AccessToken;
import fr.flst.jee.mmarie.services.AccessTokenService;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthModuleTest {

    private AuthModule authModule;

    @Before
    public void setUp() throws Exception {
        authModule = new AuthModule();
    }

    @Test
    public void testConfigure() throws Exception {

    }

    @Test
    public void testProvidesStringUserAuthenticator() throws Exception {
        AccessTokenService accessTokenService = mock(AccessTokenService.class);
        assertThat(authModule.providesStringUserAuthenticator(accessTokenService), is(notNullValue()));
    }

    @Test
    public void testProvidesBearer() throws Exception {
        BookstoreConfiguration bookstoreConfiguration = mock(BookstoreConfiguration.class);
        String bearer = "bearer-testing";
        when(bookstoreConfiguration.getBearerRealm()).thenReturn(bearer);

        assertThat(authModule.providesBearer(bookstoreConfiguration), is(bearer));
    }
}