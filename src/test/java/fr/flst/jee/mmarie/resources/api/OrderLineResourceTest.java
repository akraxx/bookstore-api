package fr.flst.jee.mmarie.resources.api;

import com.sun.jersey.api.client.GenericType;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.core.OrderLine;
import fr.flst.jee.mmarie.core.OrderLineId;
import fr.flst.jee.mmarie.dto.OrderLineDto;
import fr.flst.jee.mmarie.services.OrderLineService;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class OrderLineResourceTest {

    private static final OrderLineService orderLineService = mock(OrderLineService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new OrderLineResource(orderLineService))
            .build();

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

    private Order order1 = Order.builder()
            .id(1)
            .orderDate(new Date())
            .build();

    private Order order2 = Order.builder()
            .id(2)
            .orderDate(new Date())
            .build();

    private OrderLine orderLine1 = OrderLine.builder()
            .pk(new OrderLineId(order1, book1))
            .quantity(1)
            .build();

    private OrderLine orderLine2 = OrderLine.builder()
            .pk(new OrderLineId(order2, book2))
            .quantity(2)
            .build();

    private OrderLineDto orderLineDto1 = OrderLineDto.builder()
            .bookIsbn13("ISBN-1")
            .orderId(1)
            .quantity(1)
            .build();

    private OrderLineDto orderLineDto2 = OrderLineDto.builder()
            .bookIsbn13("ISBN-2")
            .orderId(2)
            .quantity(1)
            .build();

    @Before
    public void setup() {
        orderLine1.setBook(book1);
        orderLine1.setOrder(order1);

        orderLine2.setBook(book2);
        orderLine2.setOrder(order2);
        when(orderLineService.findByOrderId(new IntParam("1"))).thenReturn(Arrays.asList(orderLineDto1));
        when(orderLineService.findByBookIsbn13("ISBN-2")).thenReturn(Arrays.asList(orderLineDto2));
    }

    @After
    public void tearDown() {
        reset(orderLineService);
    }

    @Test
    public void testGetByOrderId() {
        assertThat(resources.client().resource("/orderLine/byOrder/1").get(new GenericType<List<OrderLineDto>>() {
        }),
                hasSize(1));
        verify(orderLineService).findByOrderId(new IntParam("1"));
    }

    @Test
    public void testGetByBookIsbn13() {
        assertThat(resources.client().resource("/orderLine/byBook/ISBN-2").get(new GenericType<List<OrderLineDto>>() {
        }),
                hasSize(1));
        verify(orderLineService).findByBookIsbn13("ISBN-2");
    }
}
