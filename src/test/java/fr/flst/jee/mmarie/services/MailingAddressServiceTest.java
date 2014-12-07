package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.db.dao.interfaces.MailingAddressDAO;
import fr.flst.jee.mmarie.dto.BookDto;
import fr.flst.jee.mmarie.dto.MailingAddressDto;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class MailingAddressServiceTest {

    @Mock
    private MailingAddressDAO mailingAddressDAO;

    @Mock
    private DtoMappingService dtoMappingService;

    private MailingAddress mailingAddress1 = MailingAddress.builder()
            .id(1)
            .city("city")
            .line1("line1")
            .build();

    private MailingAddressDto mailingAddressDto1 = MailingAddressDto.builder()
            .id(1)
            .city("city")
            .line1("line1")
            .build();

    private MailingAddressService mailingAddressService;

    @Before
    public void setup() {
        when(mailingAddressDAO.findById(1)).thenReturn(Optional.of(mailingAddress1));
        when(mailingAddressDAO.findById(0)).thenReturn(Optional.absent());

        when(dtoMappingService.convertsToDto(mailingAddress1, MailingAddressDto.class)).thenReturn(mailingAddressDto1);

        mailingAddressService = new MailingAddressService(mailingAddressDAO, dtoMappingService);
    }

    @After
    public void tearDown() {
        reset(mailingAddressDAO);
    }

    @Test
    public void testGetMailingAddress() {
        assertThat(mailingAddressService.findById(new IntParam("1")),
                is(mailingAddressDto1));
        verify(mailingAddressDAO).findById(1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetMailingAddressNotExisting() {
        mailingAddressService.findById(new IntParam("0"));
    }
}
