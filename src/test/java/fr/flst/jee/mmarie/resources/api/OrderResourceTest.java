package fr.flst.jee.mmarie.resources.api;

import com.google.common.collect.Lists;
import com.sun.jersey.api.client.GenericType;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.dto.OrderDto;
import fr.flst.jee.mmarie.services.OrderService;
import io.dropwizard.auth.oauth.OAuthProvider;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class OrderResourceTest extends ResourceTest {

    private static final OrderService orderService = mock(OrderService.class);

    private static final TestAuthenticator testAuthenticator = new TestAuthenticator();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new OrderResource(orderService))
            .addProvider(new OAuthProvider<>(testAuthenticator, "CLIENT_SECRET"))
            .build();

    private Order order1 = Order.builder()
            .id(1)
            .mailingAddress(MailingAddress.builder().id(1).build())
            .user(User.builder().login("login").build())
            .orderDate(new Date())
            .build();

    private OrderDto orderDto1 = OrderDto.builder()
            .id(1)
            .mailingAddressId(1)
            .userLogin("login")
            .orderDate(new Date())
            .build();


    @Before
    public void setup() {
        setTokenAuthorization(resources, "good-token");
        when(orderService.findById(new IntParam("1"))).thenReturn(orderDto1);
        when(orderService.findByUserLogin(testAuthenticator.getAuthenticatedUser().getLogin()))
                .thenReturn(Lists.newArrayList(orderDto1));
    }

    @After
    public void tearDown() {
        reset(orderService);
        resources.client().removeAllFilters();
    }

    @Test
    public void testGetOrder() {
        assertThat(resources.client().resource("/order/1").get(OrderDto.class),
                is(orderDto1));
        verify(orderService).findById(new IntParam("1"));
    }

    @Test
    public void testFindMyOrders() {
        assertThat(resources.client().resource("/order/mine").get(new GenericType<List<OrderDto>>() {}),
                hasItem(orderDto1));
        verify(orderService).findByUserLogin(testAuthenticator.getAuthenticatedUser().getLogin());
    }
}
