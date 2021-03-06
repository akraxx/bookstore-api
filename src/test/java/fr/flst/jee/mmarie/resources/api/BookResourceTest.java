package fr.flst.jee.mmarie.resources.api;

import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import fr.flst.jee.mmarie.dto.BookDto;
import fr.flst.jee.mmarie.services.BookService;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class BookResourceTest extends ResourceTest {

    private static final TestAuthenticator testAuthenticator = new TestAuthenticator();

    private static final BookService bookService = mock(BookService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new BookResource(bookService))
            .addProvider(new OAuthProvider<>(testAuthenticator, "CLIENT_SECRET"))
            .build();

    private BookDto bookDto1 = BookDto.builder()
            .editor("Editor1")
            .authorId(1)
            .isbn13("ISBN-1")
            .title("Title1")
            .build();

    private BookDto bookDto2 = BookDto.builder()
            .editor("Editor2")
            .authorId(2)
            .isbn13("ISBN-2")
            .title("Title2")
            .build();

    private BookDto bookDto3 = BookDto.builder()
            .editor("Editor3")
            .authorId(2)
            .isbn13("ISBN-3")
            .title("Title3")
            .build();


    @Before
    public void setup() {
        setTokenAuthorization(resources, "good-token");
        when(bookService.findAll()).thenReturn(Arrays.asList(bookDto1, bookDto2, bookDto3));
        when(bookService.findById("ISBN-1")).thenReturn(bookDto1);
        when(bookService.findByAuthorId(new IntParam("2"))).thenReturn(Arrays.asList(bookDto2, bookDto3));
        when(bookService.findByCriteriasLike(ImmutableMap.of("criteriaType", "criteriaValue"))).thenReturn(Arrays.asList(bookDto2, bookDto3));
        when(bookService.availableCriterias()).thenReturn(ImmutableMap.of("key", "value"));
    }

    @After
    public void tearDown() {
        reset(bookService);
        resources.client().removeAllFilters();
    }

    @Test
    public void testGetAllBooks() {
        List<BookDto> books = resources.client().resource("/book").get(new GenericType<List<BookDto>>() {
        });
        assertThat(books, hasSize(3));
        assertThat(books, hasItems(bookDto1, bookDto2, bookDto3));
        verify(bookService).findAll();
    }

    @Test
    public void testGetBook() {
        assertThat(resources.client().resource("/book/ISBN-1").get(BookDto.class),
                is(bookDto1));
        verify(bookService).findById("ISBN-1");
    }

    @Test
    public void testGetBookNotExisting() {
        assertEquals(204, resources.client().resource("/book/NOT-EXISTING").get(ClientResponse.class).getStatus());
        verify(bookService).findById("NOT-EXISTING");
    }

    @Test
    public void testGetBooksByAuthorId() {
        List<BookDto> books = resources.client().resource("/book/byAuthor/2").get(new GenericType<List<BookDto>>() {
        });
        assertThat(books, hasSize(2));
        assertThat(books, hasItems(bookDto2, bookDto3));
        verify(bookService).findByAuthorId(new IntParam("2"));
    }

    @Test
    public void testFindByCriteriasLike() throws Exception {
        Map<String, String> criterias = ImmutableMap.of("criteriaType", "criteriaValue");
        List<BookDto> books = resources.client().resource("/book/byCriterias")
                .entity(Map.class)
                .type(MediaType.APPLICATION_JSON)
                .post(new GenericType<List<BookDto>>() {
                }, criterias);
        assertThat(books, hasSize(2));
        assertThat(books, hasItems(bookDto2, bookDto3));
        verify(bookService).findByCriteriasLike(ImmutableMap.of("criteriaType", "criteriaValue"));
    }

    @Test
    public void testAvailableCriterias() throws Exception {
        Map<String, String> criterias = resources.client().resource("/book/criterias").get(new GenericType<Map<String, String>>() {
        });

        assertThat(criterias, hasEntry("key", "value"));
        verify(bookService, times(1)).availableCriterias();
    }
}
