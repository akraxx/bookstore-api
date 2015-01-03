package fr.flst.jee.mmarie.db.dao.hibernate;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.netflix.governator.guice.lazy.LazySingleton;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderDAO;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Hibernate implementation of {@link fr.flst.jee.mmarie.db.dao.interfaces.OrderDAO}
 */
@LazySingleton
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

    /**
     * {@inheritDoc}
     */
    @Timed(absolute = true, name = "order.dao.findById")
    @Override
    public Optional<Order> findById(Integer id) {
        return Optional.fromNullable(get(id));
    }

    @Override
    public List<Order> findByUserLogin(String login) {
        return list(namedQuery(Order.FIND_BY_USER_LOGIN).setParameter("login", login));
    }

}
