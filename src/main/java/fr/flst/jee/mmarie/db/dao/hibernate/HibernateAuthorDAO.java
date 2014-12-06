package fr.flst.jee.mmarie.db.dao.hibernate;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.netflix.governator.guice.lazy.LazySingleton;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.db.dao.interfaces.AuthorDAO;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Created by Maximilien on 16/10/2014.
 */
@LazySingleton
public class HibernateAuthorDAO extends AbstractDAO<Author> implements AuthorDAO {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    @Inject
    public HibernateAuthorDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Timed(absolute = true, name = "book.dao.findById")
    @Override
    public Optional<Author> findById(Integer id) {
        return Optional.fromNullable(get(id));
    }

}
