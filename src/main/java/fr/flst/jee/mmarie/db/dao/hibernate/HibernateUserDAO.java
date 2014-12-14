package fr.flst.jee.mmarie.db.dao.hibernate;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.netflix.governator.guice.lazy.LazySingleton;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.db.dao.interfaces.UserDAO;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

/**
 * Created by Maximilien on 16/10/2014.
 */
@LazySingleton
@Slf4j
public class HibernateUserDAO extends AbstractDAO<User> implements UserDAO {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    @Inject
    public HibernateUserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Timed(absolute = true, name = "user.dao.findByLogin")
    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.fromNullable(get(login));
    }

    @Override
    public User persist(User user) {
        log.info(user.toString());
        return persist(user);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String login, String password) {
        return Optional.fromNullable(uniqueResult(namedQuery(User.FIND_BY_USERNAME_AND_PASSWORD)
                .setParameter("login", login)
                .setParameter("password", password)));
    }

}
