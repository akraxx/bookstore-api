package fr.flst.jee.mmarie.services;

import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.core.OrderLine;
import fr.flst.jee.mmarie.core.OrderLineId;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderLineDAO;
import io.dropwizard.jersey.params.IntParam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class OrderLineServiceTest {

    private static final OrderLineDAO orderLineDAO = mock(OrderLineDAO.class);


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

    private OrderLineService orderLineService = new OrderLineService(orderLineDAO);

    @Before
    public void setup() {
        orderLine1.setBook(book1);
        orderLine1.setOrder(order1);

        orderLine2.setBook(book2);
        orderLine2.setOrder(order2);
        when(orderLineDAO.findByOrderId(1)).thenReturn(Arrays.asList(orderLine1));
        when(orderLineDAO.findByBookIsbn13("ISBN-2")).thenReturn(Arrays.asList(orderLine2));
    }

    @After
    public void tearDown() {
        reset(orderLineDAO);
    }

    @Test
    public void testGetByOrderId() {
        assertThat(orderLineService.findByOrderId(new IntParam("1")),
                hasItem(orderLine1));
        verify(orderLineDAO).findByOrderId(1);
    }

    @Test
    public void testGetByBookIsbn13() {
        assertThat(orderLineService.findByBookIsbn13("ISBN-2"),
                hasItem(orderLine2));
        verify(orderLineDAO).findByBookIsbn13("ISBN-2");
    }
}
