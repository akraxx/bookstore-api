package fr.flst.jee.mmarie.services;

import fr.flst.jee.mmarie.core.AccessToken;
import fr.flst.jee.mmarie.core.User;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class AccessTokenServiceTest {

    private User user = User.builder().login("login").build();

    private AccessTokenService accessTokenService;

    @Before
    public void beforeTest() {
        AccessTokenService.getAccessTokens().clear();
        accessTokenService = new AccessTokenService();
    }

    @Test
    public void testFindAccessTokenById() throws Exception {
        UUID uuid = UUID.randomUUID();
        AccessToken accessToken = new AccessToken(uuid, user, new DateTime());
        AccessTokenService.getAccessTokens().put(uuid, accessToken);

        assertThat(accessTokenService.findAccessTokenById(uuid).get(), is(accessToken));
    }

    @Test
    public void testFindAccessTokenById_Absent() throws Exception {
        UUID uuid = UUID.randomUUID();

        assertThat(accessTokenService.findAccessTokenById(uuid).isPresent(), is(false));
    }

    @Test
    public void testGenerateNewAccessToken() throws Exception {
        DateTime dateTime = new DateTime();
        AccessToken accessToken = accessTokenService.generateNewAccessToken(user, dateTime);

        assertThat(accessToken.getAccessTokenId(), is(notNullValue()));
        assertThat(accessToken.getAccessTokenId(), isA(UUID.class));
        assertThat(accessToken.getUser(), is(user));
        assertThat(accessToken.getLastAccessUTC(), is(dateTime));
    }

    @Test
    public void testSetLastAccessTime() throws Exception {
        UUID uuid = UUID.randomUUID();
        AccessToken accessToken = new AccessToken(uuid, user, new DateTime());
        AccessTokenService.getAccessTokens().put(uuid, accessToken);

        DateTime dateTime = new DateTime(100000000L);
        accessTokenService.setLastAccessTime(uuid, dateTime);

        assertThat(AccessTokenService.getAccessTokens().get(uuid).getLastAccessUTC(), is(dateTime));
    }

    @Test
    public void testUpdatedUser() throws Exception {
        UUID uuid = UUID.randomUUID();
        AccessToken accessToken = new AccessToken(uuid, user, new DateTime());

        UUID uuid2 = UUID.randomUUID();
        AccessToken accessToken2 = new AccessToken(uuid, User.builder().login("login2").build(), new DateTime());
        AccessTokenService.getAccessTokens().put(uuid, accessToken);
        AccessTokenService.getAccessTokens().put(uuid2, accessToken2);

        String email = "update@email.fr";
        assertThat(AccessTokenService.getAccessTokens().get(uuid).getUser().getEmail(), is(nullValue()));

        accessTokenService.updatedUser(User.builder().login("login").email(email).build());

        assertThat(AccessTokenService.getAccessTokens().get(uuid).getUser().getEmail(), is(email));
        assertThat(AccessTokenService.getAccessTokens().get(uuid2).getUser().getEmail(), is(nullValue()));
    }
}