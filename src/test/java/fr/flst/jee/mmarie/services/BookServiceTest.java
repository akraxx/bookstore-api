package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.db.dao.interfaces.BookDAO;
import fr.flst.jee.mmarie.dto.BookDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import io.dropwizard.jersey.params.IntParam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    private BookDAO bookDAO;

    @Mock
    private DtoMappingService dtoMappingService;

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

    private BookService bookService;

    private ImmutableMap<String, String> criterias =
            ImmutableMap.of("title", "Title", "author.firstName", "Author's First Name");
    @Before
    public void setup() {
        when(bookDAO.findAll()).thenReturn(Arrays.asList(book1, book2, book3));
        when(bookDAO.findById("ISBN-1")).thenReturn(Optional.of(book1));
        when(bookDAO.findById("NOT-EXISTING")).thenReturn(Optional.absent());
        when(bookDAO.findByAuthorId(2)).thenReturn(Arrays.asList(book2, book3));

        when(dtoMappingService.convertsToDto(book1, BookDto.class)).thenReturn(bookDto1);
        when(dtoMappingService.convertsListToDto(Arrays.asList(book1, book2, book3), BookDto.class))
                .thenReturn(Arrays.asList(bookDto1, bookDto2, bookDto3));
        when(dtoMappingService.convertsListToDto(Arrays.asList(book2, book3), BookDto.class))
                .thenReturn(Arrays.asList(bookDto2, bookDto3));

        bookService = new BookService(bookDAO, dtoMappingService, criterias);
    }

    @After
    public void tearDown() {
        reset(bookDAO);
    }

    @Test
    public void testGetAllBooks() {
        List<BookDto> books = bookService.findAll();
        assertThat(books, hasSize(3));
        assertThat(books, hasItems(bookDto1, bookDto2, bookDto3));
        verify(bookDAO).findAll();
    }

    @Test
    public void testGetBook() {
        assertThat(bookService.findById("ISBN-1"),
                is(bookDto1));
        verify(bookDAO).findById("ISBN-1");
    }

    @Test(expected = NotFoundException.class)
    public void testGetBookNotExisting() {
        bookService.findById("NOT-EXISTING");
    }

    @Test
    public void testGetBooksByAuthorId() {
        List<BookDto> books = bookService.findByAuthorId(new IntParam("2"));
        assertThat(books, hasSize(2));
        assertThat(books, hasItems(bookDto2, bookDto3));
        verify(bookDAO).findByAuthorId(2);
    }

    @Test
    public void testFindByCriteriasLike() throws Exception {
        ImmutableMap<String, String> myCriterias = ImmutableMap.of("title", "max");
        bookService.findByCriteriasLike(myCriterias);

        verify(bookDAO, times(1)).findByCriteriasLike(myCriterias);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByCriteriasLike_BadCriteria() throws Exception {
        ImmutableMap<String, String> myCriterias = ImmutableMap.of("badCriteria", "max");
        bookService.findByCriteriasLike(myCriterias);

        verify(bookDAO, times(0)).findByCriteriasLike(myCriterias);
    }

    @Test
    public void testAvailableCriterias() throws Exception {
        assertThat(bookService.availableCriterias(), is(criterias));
    }
}
