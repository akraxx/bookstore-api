package fr.flst.jee.mmarie.db.dao.hibernate;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderDAO;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Created by Maximilien on 16/10/2014.
 */
@Singleton
public class HibernateOrderDAO extends AbstractDAO<Order> implements OrderDAO {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    @Inject
    public HibernateOrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Timed(absolute = true, name = "order.dao.findById")
    @Override
    public Optional<Order> findById(Integer id) {
        return Optional.fromNullable(get(id));
    }

}
