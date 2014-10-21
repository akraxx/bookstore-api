package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderDAO;
import io.dropwizard.jersey.params.IntParam;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class OrderService {
    private OrderDAO orderDAO;

    public Order findSafely(Integer id) {
        final Optional<Order> order = orderDAO.findById(id);
        if (!order.isPresent()) {
            throw new NotFoundException("No such order.");
        }
        return order.get();
    }

    @Inject
    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Order findById(IntParam orderId) {
        return findSafely(orderId.get());
    }
}
