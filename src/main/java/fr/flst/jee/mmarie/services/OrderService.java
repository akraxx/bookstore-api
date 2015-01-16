package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.core.OrderLine;
import fr.flst.jee.mmarie.core.OrderLineId;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderDAO;
import fr.flst.jee.mmarie.dto.NewOrderDto;
import fr.flst.jee.mmarie.dto.OrderDto;
import fr.flst.jee.mmarie.dto.OrderLineDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import io.dropwizard.jersey.params.IntParam;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class OrderService {
    private final OrderDAO orderDAO;

    private final DtoMappingService dtoMappingService;
    private final MailingAddressService mailingAddressService;
    private final UserService userService;
    private final BookService bookService;

    public Order findSafely(Integer id) {
        final Optional<Order> order = orderDAO.findById(id);
        if (!order.isPresent()) {
            throw new NotFoundException("No such order.");
        }
        return order.get();
    }

    @Inject
    public OrderService(OrderDAO orderDAO,
                        DtoMappingService dtoMappingService,
                        MailingAddressService mailingAddressService,
                        UserService userService,
                        BookService bookService) {
        this.orderDAO = orderDAO;
        this.dtoMappingService = dtoMappingService;
        this.mailingAddressService = mailingAddressService;
        this.userService = userService;
        this.bookService = bookService;
    }

    public OrderDto findById(IntParam orderId) {
        return dtoMappingService.convertsToDto(findSafely(orderId.get()), OrderDto.class);
    }

    public List<OrderDto> findByUserLogin(String login) {
        return dtoMappingService.convertsListToDto(orderDAO.findByUserLogin(login), OrderDto.class);
    }

    public OrderDto insertNewOrder(NewOrderDto newOrderDto, String login) {
        MailingAddress persistedAddress = mailingAddressService.persist(newOrderDto.getAddress());
        Order order = new Order();
        order.setMailingAddress(persistedAddress);
        order.setOrderDate(new Date());
        order.setUser(userService.findSafely(login));
        order.setOrderLines(new HashSet<>());

        for (OrderLineDto orderLineDto : newOrderDto.getOrderLines()) {

            OrderLine orderLine = new OrderLine();
            OrderLineId orderLineId = new OrderLineId(order, bookService.findSafely(orderLineDto.getBookIsbn13()));
            orderLine.setPk(orderLineId);
            orderLine.setQuantity(orderLineDto.getQuantity());

            order.getOrderLines().add(orderLine);
        }

        return dtoMappingService.convertsToDto(orderDAO.persist(order), OrderDto.class);
    }
}
