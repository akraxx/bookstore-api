package fr.flst.jee.mmarie.resources;

import fr.flst.jee.mmarie.resources.api.ResourceTest;
import fr.flst.jee.mmarie.resources.api.TestAuthenticator;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class PingResourceTest extends ResourceTest {

    private static final TestAuthenticator testAuthenticator = new TestAuthenticator();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PingResource())
            .addProvider(new OAuthProvider<>(testAuthenticator, "CLIENT_SECRET"))
            .build();

    @Before
    public void setUp() throws Exception {
        setTokenAuthorization(resources, "good-token");
    }

    @After
    public void tearDown() throws Exception {
        resources.client().removeAllFilters();
    }

    @Test
    public void testPong() throws Exception {
        assertThat(resources.client().resource("/ping").get(String.class), containsString("pong"));
    }

    @Test
    public void testPongAuthenticated() throws Exception {
        assertThat(resources.client().resource("/ping/auth").get(String.class),
                allOf(containsString("authenticated pong"), containsString(testAuthenticator.getAuthenticatedUser().toString())));
    }
}