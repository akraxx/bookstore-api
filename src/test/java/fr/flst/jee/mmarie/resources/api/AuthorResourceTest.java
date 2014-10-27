package fr.flst.jee.mmarie.resources.api;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.services.AuthorService;
import fr.flst.jee.mmarie.services.BookService;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class AuthorResourceTest {

    private static final AuthorService authorService = mock(AuthorService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AuthorResource(authorService))
            .build();

    private Author author1 = Author.builder()
            .id(1)
            .firstName("FirstName 1")
            .lastName("LastName 1")
            .build();


    @Before
    public void setup() {
        when(authorService.findById(new IntParam("1"))).thenReturn(author1);
    }

    @After
    public void tearDown() {
        reset(authorService);
    }

    @Test
    public void testGetAuthor() {
        assertThat(resources.client().resource("/api/author/1").get(Author.class),
                is(author1));
        verify(authorService).findById(new IntParam("1"));
    }
}
