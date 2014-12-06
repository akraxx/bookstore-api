package fr.flst.jee.mmarie;

import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import fr.flst.jee.mmarie.auth.AuthModule;
import fr.flst.jee.mmarie.db.modules.HibernateModule;
import fr.flst.jee.mmarie.governator.GovernatorInjectorFactory;
import fr.flst.jee.mmarie.instrumentation.InstrumentationModule;
import fr.flst.jee.mmarie.misc.MiscModule;
import fr.flst.jee.mmarie.providers.ResourceConfigurationSourceProvider;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerDropwizard;

/**
 * Created by Maximilien on 19/10/2014.
 */
public class BookstoreApplication extends Application<BookstoreConfiguration> {

    private final SwaggerDropwizard swaggerDropwizard = new SwaggerDropwizard();

    private final HibernateBundle<BookstoreConfiguration> hibernateBundle =
            new ScanningHibernateBundle<BookstoreConfiguration>(getClass().getPackage().getName()) {
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
                .addModule(new AuthModule())
                .addModule(new MiscModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(BookstoreConfiguration.class)
                .setInjectorFactory(new GovernatorInjectorFactory())
                .build(Stage.PRODUCTION);

        bootstrap.addBundle(guiceBundle);

        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());

        swaggerDropwizard.onInitialize(bootstrap);

        bootstrap.addBundle(new AssetsBundle("/assets/webapp", "/", "index.html", "static"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run(BookstoreConfiguration configuration, Environment environment) throws Exception {
        swaggerDropwizard.onRun(configuration, environment, "localhost");

        environment.jersey().setUrlPattern("/api/*");
    }

    public static void main(String[] args) throws Exception {
        // First, initialize the database
        new BookstoreApplication().run(new String[]{"db", "migrate", "properties.yml"});

        // Then run the jetty server
        new BookstoreApplication().run(new String[]{"server", "properties.yml"});
    }
}
