package fr.flst.jee.mmarie.db.dao.hibernate;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.db.dao.interfaces.MailingAddressDAO;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Created by Maximilien on 16/10/2014.
 */
@Singleton
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

    @Timed(absolute = true, name = "mailingAddress.dao.findById")
    @Override
    public Optional<MailingAddress> findById(Integer id) {
        return Optional.fromNullable(get(id));
    }

}
