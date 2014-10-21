package fr.flst.jee.mmarie;

import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.core.OrderLine;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.db.modules.HibernateModule;
import fr.flst.jee.mmarie.instrumentation.InstrumentationModule;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by Maximilien on 19/10/2014.
 */
public class BookstoreApplication extends Application<BookstoreConfiguration> {

    private final HibernateBundle<BookstoreConfiguration> hibernateBundle =
            new HibernateBundle<BookstoreConfiguration>(Book.class, Author.class, MailingAddress.class, Order.class, User.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(BookstoreConfiguration configuration) {
                    return configuration.getDatabase();
                }
            };


    @Override
    public void initialize(Bootstrap<BookstoreConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<BookstoreConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(BookstoreConfiguration configuration) {
                return configuration.getDatabase();
            }
        });

        GuiceBundle<BookstoreConfiguration> guiceBundle = GuiceBundle.<BookstoreConfiguration>newBuilder()
                .addModule(new InstrumentationModule(bootstrap.getMetricRegistry()))
                .addModule(new HibernateModule(bootstrap, hibernateBundle))
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(BookstoreConfiguration.class)
                .build(Stage.DEVELOPMENT);

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(BookstoreConfiguration configuration, Environment environment) throws Exception {

    }

    public static void main(String[] args) throws Exception {
        // First, initialize the database
        new BookstoreApplication().run(new String[]{"db", "migrate", "properties.yml"});

        // Then run the jetty server
        new BookstoreApplication().run(new String[]{"server", "properties.yml"});
    }
}
