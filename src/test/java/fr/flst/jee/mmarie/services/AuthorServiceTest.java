package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.db.dao.interfaces.AuthorDAO;
import fr.flst.jee.mmarie.dto.AuthorDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import io.dropwizard.jersey.params.IntParam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {

    @Mock
    private AuthorDAO authorDAO;

    @Mock
    private DtoMappingService dtoMappingService;

    private Author author1 = Author.builder()
            .id(1)
            .firstName("FirstName 1")
            .lastName("LastName 1")
            .build();

    private AuthorDto authorDto1 = AuthorDto.builder()
            .id(1)
            .firstName("FirstName 1")
            .lastName("LastName 1")
            .build();


    private AuthorService authorService;

    @Before
    public void setup() {
        when(authorDAO.findById(1)).thenReturn(Optional.of(author1));
        when(authorDAO.findById(0)).thenReturn(Optional.absent());

        when(dtoMappingService.convertsToDto(author1, AuthorDto.class)).thenReturn(authorDto1);
        when(dtoMappingService.convertsToDto(author1, AuthorDto.class)).thenReturn(authorDto1);

        authorService = new AuthorService(authorDAO, dtoMappingService);
    }

    @After
    public void tearDown() {
        reset(authorDAO);
    }

    @Test
    public void testGetAuthor() {
        assertThat(authorService.findById(new IntParam("1")),
                is(authorDto1));
        verify(authorDAO).findById(1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetAuthorNotExisting() {
        authorService.findById(new IntParam("0"));
    }
}
