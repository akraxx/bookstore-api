package fr.flst.jee.mmarie.resources.api;

import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.services.UserService;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class UserResourceTest {

    private static final UserService userService = mock(UserService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UserResource(userService))
            .build();

    private User user1 = User.builder()
            .login("login")
            .email("test@test.eu")
            .password("pwd")
            .build();


    @Before
    public void setup() {
        when(userService.findByLogin("login")).thenReturn(user1);
    }

    @After
    public void tearDown() {
        reset(userService);
    }

    @Test
    public void testGetUser() {
        assertThat(resources.client().resource("/api/user/login").get(User.class),
                is(user1));
        verify(userService).findByLogin("login");
    }
}
