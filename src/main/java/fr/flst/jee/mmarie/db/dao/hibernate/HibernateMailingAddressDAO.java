package fr.flst.jee.mmarie.db.dao.hibernate;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.netflix.governator.guice.lazy.LazySingleton;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.db.dao.interfaces.MailingAddressDAO;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Hibernate implementation of {@link fr.flst.jee.mmarie.db.dao.interfaces.MailingAddressDAO}
 */
@LazySingleton
public class HibernateMailingAddressDAO extends AbstractDAO<MailingAddress> implements MailingAddressDAO {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    @Inject
    public HibernateMailingAddressDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Timed(absolute = true, name = "mailingAddress.dao.findById")
    @Override
    public Optional<MailingAddress> findById(Integer id) {
        return Optional.fromNullable(get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailingAddress persist(MailingAddress mailingAddress) {
        return super.persist(mailingAddress);
    }

}
