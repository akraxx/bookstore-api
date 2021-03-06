package fr.flst.jee.mmarie.db.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import fr.flst.jee.mmarie.BookstoreConfiguration;
import fr.flst.jee.mmarie.db.dao.hibernate.HibernateAuthorDAO;
import fr.flst.jee.mmarie.db.dao.hibernate.HibernateBookDAO;
import fr.flst.jee.mmarie.db.dao.hibernate.HibernateMailingAddressDAO;
import fr.flst.jee.mmarie.db.dao.hibernate.HibernateOrderDAO;
import fr.flst.jee.mmarie.db.dao.hibernate.HibernateOrderLineDAO;
import fr.flst.jee.mmarie.db.dao.hibernate.HibernateUserDAO;
import fr.flst.jee.mmarie.db.dao.interfaces.AuthorDAO;
import fr.flst.jee.mmarie.db.dao.interfaces.BookDAO;
import fr.flst.jee.mmarie.db.dao.interfaces.MailingAddressDAO;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderDAO;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderLineDAO;
import fr.flst.jee.mmarie.db.dao.interfaces.UserDAO;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import org.hibernate.SessionFactory;

/**
 * {@link fr.flst.jee.mmarie.db.modules.HibernateModule} configure hibernate in the {@link fr.flst.jee.mmarie.BookstoreApplication}
 */
public class HibernateModule extends AbstractModule {

    private final HibernateBundle<BookstoreConfiguration> hibernateBundle;

    public HibernateModule(Bootstrap<BookstoreConfiguration> bootstrap, HibernateBundle<BookstoreConfiguration> hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
        bootstrap.addBundle(hibernateBundle);
    }

    /**
     * Bind interfaces to hibernate implementation
     */
    @Override
    protected void configure() {
        bind(BookDAO.class).to(HibernateBookDAO.class);
        bind(AuthorDAO.class).to(HibernateAuthorDAO.class);
        bind(MailingAddressDAO.class).to(HibernateMailingAddressDAO.class);
        bind(OrderDAO.class).to(HibernateOrderDAO.class);
        bind(UserDAO.class).to(HibernateUserDAO.class);
        bind(OrderLineDAO.class).to(HibernateOrderLineDAO.class);
    }

    /**
     * Provides the session factory with {@link io.dropwizard.hibernate.HibernateBundle#getSessionFactory()}
     * @return
     */
    @Provides
    public SessionFactory provideSessionFactory() {
        return hibernateBundle.getSessionFactory();
    }
}
