package fr.flst.jee.mmarie.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.flst.jee.mmarie.core.OrderLine;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderLineDAO;
import io.dropwizard.jersey.params.IntParam;

import java.util.List;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class OrderLineService {
    private OrderLineDAO orderLineDAO;

    @Inject
    public OrderLineService(OrderLineDAO orderLineDAO) {
        this.orderLineDAO = orderLineDAO;
    }

    public List<OrderLine> findByBookIsbn13(String bookIsbn13) {
        return orderLineDAO.findByBookIsbn13(bookIsbn13);
    }

    public List<OrderLine> findByOrderId(IntParam orderId) {
        return orderLineDAO.findByOrderId(orderId.get());
    }
}
