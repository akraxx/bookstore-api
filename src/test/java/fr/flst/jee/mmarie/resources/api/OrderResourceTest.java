package fr.flst.jee.mmarie.resources.api;

import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.dto.OrderDto;
import fr.flst.jee.mmarie.services.OrderService;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class OrderResourceTest {

    private static final OrderService orderService = mock(OrderService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new OrderResource(orderService))
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
        when(orderService.findById(new IntParam("1"))).thenReturn(orderDto1);
    }

    @After
    public void tearDown() {
        reset(orderService);
    }

    @Test
    public void testGetOrder() {
        assertThat(resources.client().resource("/order/1").get(OrderDto.class),
                is(orderDto1));
        verify(orderService).findById(new IntParam("1"));
    }
}
