package fr.flst.jee.mmarie.resources.api;

import fr.flst.jee.mmarie.dto.MailingAddressDto;
import fr.flst.jee.mmarie.services.MailingAddressService;
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
public class MailingAddressResourceTest {

    private static final MailingAddressService mailingAddressService = mock(MailingAddressService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MailingAddressResource(mailingAddressService, null))
            .build();

    private MailingAddressDto mailingAddressDto1 = MailingAddressDto.builder()
            .id(1)
            .city("city")
            .line1("line1")
            .build();

    @Before
    public void setup() {
        when(mailingAddressService.findById(new IntParam("1"))).thenReturn(mailingAddressDto1);
    }

    @After
    public void tearDown() {
        reset(mailingAddressService);
    }

    @Test
    public void testGetMailingAddress() {
        assertThat(resources.client().resource("/mailingAddress/1").get(MailingAddressDto.class),
                is(mailingAddressDto1));
        verify(mailingAddressService).findById(new IntParam("1"));
    }
}
