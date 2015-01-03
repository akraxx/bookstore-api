package fr.flst.jee.mmarie.resources.api;

import fr.flst.jee.mmarie.dto.AuthorDto;
import fr.flst.jee.mmarie.services.AuthorService;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.jersey.params.IntParam;
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
public class AuthorResourceTest extends ResourceTest {

    private static final AuthorService authorService = mock(AuthorService.class);

    private static final TestAuthenticator testAuthenticator = new TestAuthenticator();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AuthorResource(authorService))
            .addProvider(new OAuthProvider<>(testAuthenticator, "CLIENT_SECRET"))
            .build();

    private AuthorDto authorDto1 = AuthorDto.builder()
            .id(1)
            .firstName("FirstName 1")
            .lastName("LastName 1")
            .build();

    @Before
    public void setup() {
        setTokenAuthorization(resources, "good-token");
        when(authorService.findById(new IntParam("1"))).thenReturn(authorDto1);
    }

    @After
    public void tearDown() {
        reset(authorService);
        resources.client().removeAllFilters();
    }

    @Test
    public void testGetAuthor() {
        assertThat(resources.client().resource("/author/1").get(AuthorDto.class),
                is(authorDto1));
        verify(authorService).findById(new IntParam("1"));
    }
}
