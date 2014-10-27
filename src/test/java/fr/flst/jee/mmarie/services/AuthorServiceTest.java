package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.db.dao.interfaces.AuthorDAO;
import io.dropwizard.jersey.params.IntParam;
import org.junit.After;
import org.junit.Before;
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
public class AuthorServiceTest {

    private static final AuthorDAO authorDAO = mock(AuthorDAO.class);

    private Author author1 = Author.builder()
            .id(1)
            .firstName("FirstName 1")
            .lastName("LastName 1")
            .build();


    private AuthorService authorService = new AuthorService(authorDAO);

    @Before
    public void setup() {
        when(authorDAO.findById(1)).thenReturn(Optional.of(author1));
        when(authorDAO.findById(0)).thenReturn(Optional.absent());
    }

    @After
    public void tearDown() {
        reset(authorDAO);
    }

    @Test
    public void testGetAuthor() {
        assertThat(authorService.findById(new IntParam("1")),
                is(author1));
        verify(authorDAO).findById(1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetAuthorNotExisting() {
        authorService.findById(new IntParam("0"));
    }
}
