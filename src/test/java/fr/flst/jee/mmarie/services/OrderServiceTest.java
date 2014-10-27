package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderDAO;
import io.dropwizard.jersey.params.IntParam;
import org.junit.After;
import org.junit.Before;
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
public class OrderServiceTest {

    private static final OrderDAO orderDAO = mock(OrderDAO.class);

    private Order order1 = Order.builder()
            .id(1)
            .orderDate(new Date())
            .build();

    private OrderService orderService = new OrderService(orderDAO);

    @Before
    public void setup() {
        when(orderDAO.findById(1)).thenReturn(Optional.of(order1));
        when(orderDAO.findById(0)).thenReturn(Optional.absent());
    }

    @After
    public void tearDown() {
        reset(orderDAO);
    }

    @Test
    public void testGetOrder() {
        assertThat(orderService.findById(new IntParam("1")),
                is(order1));
        verify(orderDAO).findById(1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetMailingAddressNotExisting() {
        orderService.findById(new IntParam("0"));
    }
}
