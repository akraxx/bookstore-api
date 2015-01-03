package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderDAO;
import fr.flst.jee.mmarie.dto.OrderDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import io.dropwizard.jersey.params.IntParam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private DtoMappingService dtoMappingService;

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

    private OrderService orderService;

    @Before
    public void setup() {
        when(orderDAO.findById(1)).thenReturn(Optional.of(order1));
        when(orderDAO.findByUserLogin("login")).thenReturn(Lists.newArrayList(order1));
        when(orderDAO.findById(0)).thenReturn(Optional.absent());

        when(dtoMappingService.convertsListToDto(Lists.newArrayList(order1), OrderDto.class))
                .thenReturn(Lists.newArrayList(orderDto1));
        when(dtoMappingService.convertsToDto(order1, OrderDto.class)).thenReturn(orderDto1);

        orderService = new OrderService(orderDAO, dtoMappingService);
    }

    @After
    public void tearDown() {
        reset(orderDAO);
    }

    @Test
    public void testGetOrder() {
        assertThat(orderService.findById(new IntParam("1")),
                is(orderDto1));
        verify(orderDAO).findById(1);
    }

    @Test
    public void testFindByUserLogin() {
        assertThat(orderService.findByUserLogin("login"),
                hasItem(orderDto1));
        verify(orderDAO).findByUserLogin("login");
    }

    @Test(expected = NotFoundException.class)
    public void testGetMailingAddressNotExisting() {
        orderService.findById(new IntParam("0"));
    }
}
