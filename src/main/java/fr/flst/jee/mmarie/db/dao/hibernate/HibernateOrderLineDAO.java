package fr.flst.jee.mmarie.db.dao.hibernate;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.netflix.governator.guice.lazy.LazySingleton;
import fr.flst.jee.mmarie.core.OrderLine;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderLineDAO;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Maximilien on 16/10/2014.
 */
@LazySingleton
public class HibernateOrderLineDAO extends AbstractDAO<OrderLine> implements OrderLineDAO {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    @Inject
    public HibernateOrderLineDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Timed(absolute = true, name = "orderLine.dao.findByBookIsbn13")
    @Override
    public List<OrderLine> findByBookIsbn13(String bookIsbn13) {
        return list(namedQuery(OrderLine.FIND_BY_BOOK_ISBN13).setParameter("bookIsbn13", bookIsbn13));
    }

    @Timed(absolute = true, name = "orderLine.dao.findByOrderId")
    @Override
    public List<OrderLine> findByOrderId(Integer orderId) {
        return list(namedQuery(OrderLine.FIND_BY_ORDER_ID).setParameter("orderId", orderId));
    }

}
