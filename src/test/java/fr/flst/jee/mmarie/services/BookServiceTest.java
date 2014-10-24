package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.db.dao.interfaces.BookDAO;
import io.dropwizard.jersey.params.IntParam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class BookServiceTest {
    private static final BookDAO bookDAO = mock(BookDAO.class);

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

    private BookService bookService = new BookService(bookDAO);

    @Before
    public void setup() {
        when(bookDAO.findAll()).thenReturn(Arrays.asList(book1, book2, book3));
        when(bookDAO.findById("ISBN-1")).thenReturn(Optional.of(book1));
        when(bookDAO.findById("NOT-EXISTING")).thenReturn(Optional.absent());
        when(bookDAO.findByAuthorId(2)).thenReturn(Arrays.asList(book2, book3));
    }

    @After
    public void tearDown() {
        reset(bookDAO);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = bookService.findAll();
        assertThat(books, hasSize(3));
        assertThat(books, hasItems(book1, book2, book3));
        verify(bookDAO).findAll();
    }

    @Test
    public void testGetBook() {
        assertThat(bookService.findById("ISBN-1"),
                is(book1));
        verify(bookDAO).findById("ISBN-1");
    }

    @Test(expected = NotFoundException.class)
    public void testGetBookNotExisting() {
        bookService.findById("NOT-EXISTING");
    }

    @Test
    public void testGetBooksByAuthorId() {
        List<Book> books = bookService.findByAuthorId(new IntParam("2"));
        assertThat(books, hasSize(2));
        assertThat(books, hasItems(book2, book3));
        verify(bookDAO).findByAuthorId(2);
    }
    
}
