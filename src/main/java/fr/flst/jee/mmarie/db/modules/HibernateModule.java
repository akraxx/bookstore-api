package fr.flst.jee.mmarie.db.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import fr.flst.jee.mmarie.BookstoreConfiguration;
import fr.flst.jee.mmarie.db.dao.hibernate.HibernateAuthorDAO;
import fr.flst.jee.mmarie.db.dao.hibernate.HibernateBookDAO;
import fr.flst.jee.mmarie.db.dao.interfaces.AuthorDAO;
import fr.flst.jee.mmarie.db.dao.interfaces.BookDAO;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Maximilien on 17/10/2014.
 */

public class HibernateModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateModule.class);

    private final HibernateBundle hibernateBundle;

    public HibernateModule(Bootstrap<BookstoreConfiguration> bootstrap, HibernateBundle hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    protected void configure() {
        bind(BookDAO.class).to(HibernateBookDAO.class);
        bind(AuthorDAO.class).to(HibernateAuthorDAO.class);
    }

    @Provides
    public SessionFactory provideSessionFactory(BookstoreConfiguration configuration,
                                                Environment environment) {
        return hibernateBundle.getSessionFactory();
    }
}
