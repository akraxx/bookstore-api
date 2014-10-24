package fr.flst.jee.mmarie.resources.api;

import com.oracle.webservices.internal.api.databinding.Databinding;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.db.dao.interfaces.BookDAO;
import fr.flst.jee.mmarie.services.BookService;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static org.hamcrest.Matchers.hasItem;
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

    private Book book1 = Book.builder()
            .editor("Editor1")
            .author(Author.builder().id(1).build())
            .isbn13("ISBN-1")
            .title("Title1")
            .build();

    private Book book2 = Book.builder()
            .editor("Editor2")
            .author(Author.builder().id(2).build())
            .isbn13("ISBN-2")
            .title("Title2")
            .build();

    private Book book3 = Book.builder()
            .editor("Editor3")
            .author(Author.builder().id(2).build())
            .isbn13("ISBN-3")
            .title("Title3")
            .build();


    @Before
    public void setup() {
        when(bookService.findAll()).thenReturn(Arrays.asList(book1, book2, book3));
        when(bookService.findById("ISBN-1")).thenReturn(book1);
        when(bookService.findByAuthorId(new IntParam("2"))).thenReturn(Arrays.asList(book2, book3));
    }

    @After
    public void tearDown() {
        reset(bookService);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = resources.client().resource("/api/book").get(new GenericType<List<Book>>() {});
        assertThat(books, hasSize(3));
        assertThat(books, hasItems(book1, book2, book3));
        verify(bookService).findAll();
    }

    @Test
    public void testGetBook() {
        assertThat(resources.client().resource("/api/book/ISBN-1").get(Book.class),
                is(book1));
        verify(bookService).findById("ISBN-1");
    }

    @Test
    public void testGetBookNotExisting() {
        assertEquals(204, resources.client().resource("/api/book/NOT-EXISTING").get(ClientResponse.class).getStatus());
        verify(bookService).findById("NOT-EXISTING");
    }

    @Test
    public void testGetBooksByAuthorId() {
        List<Book> books = resources.client().resource("/api/book/byAuthor/2").get(new GenericType<List<Book>>() {});
        assertThat(books, hasSize(2));
        assertThat(books, hasItems(book2, book3));
        verify(bookService).findByAuthorId(new IntParam("2"));
    }
}
