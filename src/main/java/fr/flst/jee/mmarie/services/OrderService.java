package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderDAO;
import fr.flst.jee.mmarie.dto.OrderDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import io.dropwizard.jersey.params.IntParam;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class OrderService {
    private final OrderDAO orderDAO;

    private final DtoMappingService dtoMappingService;

    public Order findSafely(Integer id) {
        final Optional<Order> order = orderDAO.findById(id);
        if (!order.isPresent()) {
            throw new NotFoundException("No such order.");
        }
        return order.get();
    }

    @Inject
    public OrderService(OrderDAO orderDAO, DtoMappingService dtoMappingService) {
        this.orderDAO = orderDAO;
        this.dtoMappingService = dtoMappingService;
    }

    public OrderDto findById(IntParam orderId) {
        return dtoMappingService.convertsToDto(findSafely(orderId.get()), OrderDto.class);
    }
}
