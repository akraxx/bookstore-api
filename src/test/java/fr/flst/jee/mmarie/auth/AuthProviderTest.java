package fr.flst.jee.mmarie.auth;

import fr.flst.jee.mmarie.core.User;
import io.dropwizard.auth.Authenticator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthProviderTest {

    @Mock
    private Authenticator<String, User> authenticator;

    private String realm = "realm-testing";

    private AuthProvider authProvider;

    @Before
    public void setUp() throws Exception {
        authProvider = new AuthProvider(authenticator, realm);
    }

    @Test
    public void testConstructor() throws Exception {
        assertThat(authProvider, is(notNullValue()));
    }
}