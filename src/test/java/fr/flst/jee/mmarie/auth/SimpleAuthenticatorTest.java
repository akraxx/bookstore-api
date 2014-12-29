package fr.flst.jee.mmarie.auth;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.AccessToken;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.services.AccessTokenService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleAuthenticatorTest {

    @Mock
    private AccessTokenService accessTokenService;

    private SimpleAuthenticator simpleAuthenticator;

    private String uuidNullToken = "6f258815-2f34-45f7-b684-f1d38bba8e60";
    private String uuidAbsentToken = "0c3525ab-fb8c-4153-bb16-ee996a52ce9f";
    private String uuidExpiredToken = "d410ce63-f3ee-482d-8620-3f056ba4fc01";
    private String uuidGoodToken = "6e4f2d67-04d0-4d6d-8b69-0eaa4d08c993";

    private User user = User.builder().login("authuser").build();

    private AccessToken accessTokenExpired = new AccessToken(UUID.fromString(uuidExpiredToken),
            user,
            new DateTime().minusMinutes(40));

    private AccessToken accessToken = new AccessToken(UUID.fromString(uuidGoodToken),
            user,
            new DateTime().minusMinutes(20));

    @Before
    public void setUp() throws Exception {
        simpleAuthenticator = new SimpleAuthenticator(accessTokenService);

        when(accessTokenService.findAccessTokenById(UUID.fromString(uuidNullToken))).thenReturn(null);
        when(accessTokenService.findAccessTokenById(UUID.fromString(uuidAbsentToken))).thenReturn(Optional.absent());
        when(accessTokenService.findAccessTokenById(UUID.fromString(uuidExpiredToken))).thenReturn(Optional.of(accessTokenExpired));
        when(accessTokenService.findAccessTokenById(UUID.fromString(uuidGoodToken))).thenReturn(Optional.of(accessToken));
    }

    @Test
    public void testAuthenticate_Bad_UUID() throws Exception {
        assertThat(simpleAuthenticator.authenticate("bad-uuid").isPresent(), is(false));

        verify(accessTokenService, times(0)).setLastAccessTime(any(UUID.class), any(DateTime.class));
    }

    @Test
    public void testAuthenticate_Null_AccessToken() throws Exception {
        assertThat(simpleAuthenticator.authenticate(uuidNullToken).isPresent(), is(false));

        verify(accessTokenService, times(0)).setLastAccessTime(any(UUID.class), any(DateTime.class));
    }

    @Test
    public void testAuthenticate_Absent_AccessToken() throws Exception {
        assertThat(simpleAuthenticator.authenticate(uuidAbsentToken).isPresent(), is(false));

        verify(accessTokenService, times(0)).setLastAccessTime(any(UUID.class), any(DateTime.class));
    }

    @Test
    public void testAuthenticate_Expired_AccessToken() throws Exception {
        assertThat(simpleAuthenticator.authenticate(uuidExpiredToken).isPresent(), is(false));

        verify(accessTokenService, times(0)).setLastAccessTime(any(UUID.class), any(DateTime.class));
    }

    @Test
    public void testAuthenticate_Good_AccessToken() throws Exception {
        assertThat(simpleAuthenticator.authenticate(uuidGoodToken).get(), is(user));

        verify(accessTokenService, times(1)).setLastAccessTime(any(UUID.class), any(DateTime.class));
    }
}