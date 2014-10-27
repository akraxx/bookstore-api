package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.db.dao.interfaces.MailingAddressDAO;
import fr.flst.jee.mmarie.resources.api.MailingAddressResource;
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
public class MailingAddressServiceTest {

    private static final MailingAddressDAO mailingAddressDAO = mock(MailingAddressDAO.class);

    private MailingAddress mailingAddress1 = MailingAddress.builder()
            .id(1)
            .city("city")
            .line1("line1")
            .build();

    private MailingAddressService mailingAddressService = new MailingAddressService(mailingAddressDAO);

    @Before
    public void setup() {
        when(mailingAddressDAO.findById(1)).thenReturn(Optional.of(mailingAddress1));
        when(mailingAddressDAO.findById(0)).thenReturn(Optional.absent());
    }

    @After
    public void tearDown() {
        reset(mailingAddressDAO);
    }

    @Test
    public void testGetMailingAddress() {
        assertThat(mailingAddressService.findById(new IntParam("1")),
                is(mailingAddress1));
        verify(mailingAddressDAO).findById(1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetMailingAddressNotExisting() {
        mailingAddressService.findById(new IntParam("0"));
    }
}
