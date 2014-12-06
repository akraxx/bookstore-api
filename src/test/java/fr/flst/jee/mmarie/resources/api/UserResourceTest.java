package fr.flst.jee.mmarie.resources.api;

import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.dto.UserDto;
import fr.flst.jee.mmarie.services.AccessTokenService;
import fr.flst.jee.mmarie.services.UserService;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class UserResourceTest extends ResourceTest {

    private static final UserService userService = mock(UserService.class);

    private static final AccessTokenService accessTokenService = mock(AccessTokenService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UserResource(userService, accessTokenService))
            .addProvider(new OAuthProvider<>(new TestAuthenticator(), "CLIENT_SECRET"))
            .build();

    private User user1 = User.builder()
            .login("login")
            .email("test@test.eu")
            .password("pwd")
            .build();

    private UserDto userDto1 = UserDto.builder()
            .login("login")
            .email("test@test.eu")
            .password("pwd")
            .build();

    @Before
    public void setup() {
        setTokenAuthorization(resources, "good-token");
        when(userService.findByLogin("login")).thenReturn(userDto1);
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
}
