package fr.flst.jee.mmarie.resources.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Optional;
import com.sun.jersey.api.client.ClientResponse;
import fr.flst.jee.mmarie.core.AccessToken;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.dto.UserDto;
import fr.flst.jee.mmarie.services.AccessTokenService;
import fr.flst.jee.mmarie.services.UserService;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class UserResourceTest extends ResourceTest {

    private static final UserService userService = mock(UserService.class);
    private static final AccessTokenService accessTokenService = mock(AccessTokenService.class);

    private static final TestAuthenticator testAuthenticator = new TestAuthenticator();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UserResource(userService, accessTokenService))
            .addProvider(new OAuthProvider<>(testAuthenticator, "CLIENT_SECRET"))
            .build();

    private User user1 = User.builder()
            .login("login")
            .email("test@test.eu")
            .password("mypassword")
            .build();

    private UserDto userDto1 = UserDto.builder()
            .login("login")
            .email("test@test.eu")
            .password("pwd")
            .build();

    private String updateMail = "updated@test.fr";
    private String updatePwd = "mypassword";

    @Before
    public void setup() {
        setTokenAuthorization(resources, "good-token");
        when(userService.findByLogin("login")).thenReturn(userDto1);
        when(userService.findMe(testAuthenticator.getAuthenticatedUser())).thenReturn(userDto1);
        when(userService.updateEmail(testAuthenticator.getAuthenticatedUser(), updateMail)).thenReturn(userDto1);
        when(userService.updatePassword(testAuthenticator.getAuthenticatedUser(), updatePwd)).thenReturn(userDto1);
        when(userService.insert(user1)).thenReturn(userDto1);
    }

    @After
    public void tearDown() {
        reset(userService);
        resources.client().removeAllFilters();
    }

    @Test
    public void testGetUser() {
        assertThat(resources.client().resource("/user/login").get(UserDto.class),
                is(userDto1));
        verify(userService).findByLogin("login");
    }

    @Test
    public void testGetMe() {
        assertThat(resources.client().resource("/user/me").get(UserDto.class),
                is(userDto1));
        verify(userService).findMe(testAuthenticator.getAuthenticatedUser());
    }

    @Test
    public void testUpdateEmail() {
        assertThat(resources.client().resource("/user/updateEmail").put(UserDto.class, updateMail),
                is(userDto1));

        verify(userService).updateEmail(testAuthenticator.getAuthenticatedUser(), updateMail);
    }

    @Test
    public void testUpdatePassword() {
        assertThat(resources.client().resource("/user/updatePassword").put(UserDto.class, updatePwd),
                is(userDto1));

        verify(userService).updatePassword(testAuthenticator.getAuthenticatedUser(), updatePwd);
    }

    @Test
    public void testInsert() throws JsonProcessingException {
        assertThat(resources.client().resource("/user")
                        .type(MediaType.APPLICATION_JSON)
                        .post(UserDto.class, objectMapper.writeValueAsString(user1)),
                is(userDto1));

        verify(userService).insert(user1);
    }

    @Test
     public void testAuthorize_NullUser() {
        assertThat(resources.client().resource("/user/authenticate")
                        .type(MediaType.APPLICATION_FORM_URLENCODED)
                        .post(ClientResponse.class, "username=username&password=password").getStatus(),
                is(401));

        verify(userService, times(1)).findByUsernameAndPassword("username", "password");

    }

    @Test
    public void testAuthorize_NotPresentUser() {

        when(userService.findByUsernameAndPassword("username", "password")).thenReturn(Optional.<User>absent());

        assertThat(resources.client().resource("/user/authenticate")
                        .type(MediaType.APPLICATION_FORM_URLENCODED)
                        .post(ClientResponse.class, "username=username&password=password").getStatus(),
                is(401));

        verify(userService, times(1)).findByUsernameAndPassword("username", "password");
    }

    @Test
    public void testAuthorize_PresentUser() {

        when(userService.findByUsernameAndPassword("username", "password")).thenReturn(Optional.of(user1));
        UUID accessTokenId = UUID.randomUUID();
        when(accessTokenService.generateNewAccessToken(any(User.class), any(DateTime.class))).thenReturn(new AccessToken(accessTokenId, user1, new DateTime()));

        assertThat(resources.client().resource("/user/authenticate")
                        .type(MediaType.APPLICATION_FORM_URLENCODED)
                        .post(String.class, "username=username&password=password"),
                is(accessTokenId.toString()));

        verify(userService, times(1)).findByUsernameAndPassword("username", "password");
        verify(accessTokenService, times(1)).generateNewAccessToken(any(User.class), any(DateTime.class));

    }
}
