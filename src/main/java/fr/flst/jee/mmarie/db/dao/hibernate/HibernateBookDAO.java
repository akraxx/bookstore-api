package fr.flst.jee.mmarie.db.dao.hibernate;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.netflix.governator.guice.lazy.LazySingleton;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.db.dao.interfaces.BookDAO;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Maximilien on 16/10/2014.
 */
@LazySingleton
public class HibernateBookDAO extends AbstractDAO<Book> implements BookDAO {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    @Inject
    public HibernateBookDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Timed(absolute = true, name = "book.dao.findById")
    @Override
    public Optional<Book> findById(String isbn13) {
        return Optional.fromNullable(get(isbn13));
    }

    @Override
    public List<Book> findByAuthorId(Integer authorId) {
        return list(namedQuery(Book.FIND_BY_AUTHOR_ID).setParameter("authorId", authorId));
    }

    @Override
    public List<Book> findAll() {
        return list(namedQuery(Book.FIND_ALL));
    }

}
