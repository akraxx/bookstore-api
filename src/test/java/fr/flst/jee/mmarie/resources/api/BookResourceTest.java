package fr.flst.jee.mmarie.resources.api;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import fr.flst.jee.mmarie.dto.BookDto;
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
public class BookResourceTest {

    private static final BookService bookService = mock(BookService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new BookResource(bookService))
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
        when(bookService.findAll()).thenReturn(Arrays.asList(bookDto1, bookDto2, bookDto3));
        when(bookService.findById("ISBN-1")).thenReturn(bookDto1);
        when(bookService.findByAuthorId(new IntParam("2"))).thenReturn(Arrays.asList(bookDto2, bookDto3));
    }

    @After
    public void tearDown() {
        reset(bookService);
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
}
