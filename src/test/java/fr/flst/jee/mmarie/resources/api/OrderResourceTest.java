package fr.flst.jee.mmarie.resources.api;

import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.services.AuthorService;
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
            .orderDate(new Date())
            .build();


    @Before
    public void setup() {
        when(orderService.findById(new IntParam("1"))).thenReturn(order1);
    }

    @After
    public void tearDown() {
        reset(orderService);
    }

    @Test
    public void testGetOrder() {
        assertThat(resources.client().resource("/api/order/1").get(Order.class),
                is(order1));
        verify(orderService).findById(new IntParam("1"));
    }
}
