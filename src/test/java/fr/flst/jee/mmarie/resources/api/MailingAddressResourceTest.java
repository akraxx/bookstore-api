package fr.flst.jee.mmarie.resources.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.dto.MailingAddressDto;
import fr.flst.jee.mmarie.services.MailingAddressService;
import fr.flst.jee.mmarie.services.UserService;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class MailingAddressResourceTest extends ResourceTest {

    private static final MailingAddressService mailingAddressService = mock(MailingAddressService.class);
    private static final UserService userService = mock(UserService.class);

    private static final TestAuthenticator testAuthenticator = new TestAuthenticator();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MailingAddressResource(mailingAddressService, userService))
            .addProvider(new OAuthProvider<>(testAuthenticator, "CLIENT_SECRET"))
            .build();

    private MailingAddressDto mailingAddressDto1 = MailingAddressDto.builder()
            .id(1)
            .city("city")
            .line1("line1")
            .build();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        setTokenAuthorization(resources, "good-token");
        when(mailingAddressService.findById(new IntParam("1"))).thenReturn(mailingAddressDto1);
    }

    @After
    public void tearDown() {
        reset(mailingAddressService, userService);
        resources.client().removeAllFilters();
    }

    @Test
    public void testGetMailingAddress() {
        assertThat(resources.client().resource("/mailingAddress/1").get(MailingAddressDto.class),
                is(mailingAddressDto1));
        verify(mailingAddressService).findById(new IntParam("1"));
    }

    @Test(expected = Exception.class)
    public void testUpdate_NotValid() throws JsonProcessingException {
        MailingAddressDto addressDto = MailingAddressDto.builder()
                .zip("59000")
                .city("Lille")
                .build();

        resources.client().resource("/mailingAddress")
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .put(MailingAddressDto.class, objectMapper.writeValueAsString(addressDto));
    }

    @Test
    public void testUpdate_NullMailingAddress() throws JsonProcessingException {
        MailingAddressDto addressDto = MailingAddressDto.builder()
                .zip("59000")
                .city("Lille")
                .line1("41 bd vauban")
                .build();

        MailingAddress mailingAddress = MailingAddress.builder()
                .zip("59000")
                .city("Lille")
                .line1("41 bd vauban")
                .build();

        testAuthenticator.getAuthenticatedUser().setMailingAddress(null);

        when(mailingAddressService.persist(addressDto)).thenReturn(mailingAddress);
        when(mailingAddressService.converts(mailingAddress)).thenReturn(addressDto);

        assertThat(resources.client().resource("/mailingAddress")
                        .accept(MediaType.APPLICATION_JSON)
                        .type(MediaType.APPLICATION_JSON)
                        .put(MailingAddressDto.class, objectMapper.writeValueAsString(addressDto)),
                is(addressDto));

        verify(mailingAddressService, times(1)).persist(addressDto);
        verify(mailingAddressService, times(1)).converts(mailingAddress);
        verify(userService, times(1)).updateMailingAddress(testAuthenticator.getAuthenticatedUser(), mailingAddress);
    }

    @Test
    public void testUpdate_NotEquals_MailingAddress() throws JsonProcessingException {
        MailingAddressDto addressDto = MailingAddressDto.builder()
                .zip("59000")
                .city("Lille")
                .id(1)
                .line1("41 bd vauban")
                .build();

        MailingAddress mailingAddress = MailingAddress.builder()
                .zip("59000")
                .city("Lille")
                .line1("41 bd vauban")
                .build();

        testAuthenticator.getAuthenticatedUser().setMailingAddress(MailingAddress.builder().id(2).build());

        when(mailingAddressService.persist(addressDto)).thenReturn(mailingAddress);
        when(mailingAddressService.converts(mailingAddress)).thenReturn(addressDto);

        assertThat(resources.client().resource("/mailingAddress")
                        .accept(MediaType.APPLICATION_JSON)
                        .type(MediaType.APPLICATION_JSON)
                        .put(MailingAddressDto.class, objectMapper.writeValueAsString(addressDto)),
                is(addressDto));

        verify(mailingAddressService, times(1)).persist(addressDto);
        verify(mailingAddressService, times(1)).converts(mailingAddress);
        verify(userService, times(1)).updateMailingAddress(testAuthenticator.getAuthenticatedUser(), mailingAddress);
    }

    @Test
    public void testUpdate_Equals_MailingAddress() throws JsonProcessingException {
        MailingAddressDto addressDto = MailingAddressDto.builder()
                .zip("59000")
                .city("Lille")
                .id(1)
                .line1("41 bd vauban")
                .build();

        MailingAddress mailingAddress = MailingAddress.builder()
                .zip("59000")
                .city("Lille")
                .line1("41 bd vauban")
                .build();

        testAuthenticator.getAuthenticatedUser().setMailingAddress(MailingAddress.builder().id(1).build());

        when(mailingAddressService.persist(addressDto)).thenReturn(mailingAddress);
        when(mailingAddressService.converts(mailingAddress)).thenReturn(addressDto);

        assertThat(resources.client().resource("/mailingAddress")
                        .accept(MediaType.APPLICATION_JSON)
                        .type(MediaType.APPLICATION_JSON)
                        .put(MailingAddressDto.class, objectMapper.writeValueAsString(addressDto)),
                is(addressDto));

        verify(mailingAddressService, times(1)).persist(addressDto);
        verify(mailingAddressService, times(1)).converts(mailingAddress);
        verify(userService, times(0)).updateMailingAddress(testAuthenticator.getAuthenticatedUser(), mailingAddress);
    }
}
