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
 * Main class. Defines how the application will work.
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


    /**
     * <p>
     *     First, add a {@link io.dropwizard.migrations.MigrationsBundle} to work with the database when a migration command is run. This bundle
     *     needs only the {@link io.dropwizard.db.DataSourceFactory} from the {@link fr.flst.jee.mmarie.BookstoreConfiguration}.
     * </p>
     *
     * The most important bundle of the application is the {@link com.hubspot.dropwizard.guice.GuiceBundle} which will automatically inject all
     * the classes which implements, extends or annotated with :
     * <ul>
     *     <li>{@link io.dropwizard.lifecycle.Managed}</li>
     *     <li>{@link io.dropwizard.servlets.tasks.Task}</li>
     *     <li>{@link com.hubspot.dropwizard.guice.InjectableHealthCheck}</li>
     *     <li>{@link com.sun.jersey.spi.inject.InjectableProvider}</li>
     *     <li>{@link javax.ws.rs.ext.Provider}</li>
     *     <li>{@link javax.ws.rs.Path}</li>
     * </ul>
     *
     * I added some {@link com.google.inject.Module} to the bundle ({@link com.hubspot.dropwizard.guice.GuiceBundle.Builder#addModule(com.google.inject.Module)} :
     *
     * <ul>
     *     <li>
     *         {@link fr.flst.jee.mmarie.instrumentation.InstrumentationModule}, useful to time methods and register it into
     *         the dropwizard {@link com.codahale.metrics.MetricRegistry} available <a href="http://localhost:9091/">here</a>.
     *     </li>
     *     <li>
     *         {@link fr.flst.jee.mmarie.db.modules.HibernateModule}, used to bind dao interfaces to hibernate implementations. Also provided
     *         an instance of {@link org.hibernate.SessionFactory}.
     *     </li>
     *     <li>
     *         {@link fr.flst.jee.mmarie.auth.AuthModule}, tools for the OAuth authentication used in the whole application.
     *     </li>
     *     <li>
     *         {@link fr.flst.jee.mmarie.misc.MiscModule}, provides some instances like a {@link org.dozer.Mapper} for dto mapping.
     *     </li>
     * </ul>
     *
     * <p>
     *     The configuration is in the classpath, I created my own {@link fr.flst.jee.mmarie.providers.ResourceConfigurationSourceProvider} which implements
     *     {@link io.dropwizard.configuration.ConfigurationSourceProvider} which will search the given configuration name in the classpath
     *     instead of using the {@link java.net.URL} class.
     * </p>
     *
     * <p>
     *     An {@link io.dropwizard.assets.AssetsBundle} is added to redirect all root http request to the angular webapp stored in the
     *     classpath under the folder "assets/webapp".
     * </p>
     *
     * @see <a href="https://github.com/HubSpot/dropwizard-guice/">dropwizard-guice</a>
     * @param bootstrap bootstrap provided by dropwizard
     */
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

    /**
     * <p>
     *     We use this method only to set the prefix of each jersey url of our application to "/api/*". <br>
     *     {@link io.dropwizard.jersey.setup.JerseyEnvironment#setUrlPattern(String)}
     * </p>
     *
     * @param configuration {@link fr.flst.jee.mmarie.BookstoreConfiguration} of the application
     * @param environment The current {@link io.dropwizard.setup.Environment}
     * @throws Exception stops the application if an error occurred
     */
    @Override
    public void run(BookstoreConfiguration configuration, Environment environment) throws Exception {
        swaggerDropwizard.onRun(configuration, environment, "localhost");

        environment.jersey().setUrlPattern("/api/*");
    }

    /**
     * <ul>
     *     <li>
     *         Run the application with the command : <b>db migrate properties.yml</b>. Initialize the database with the file "migrations.xml"
     *         in the classpath.
     *     </li>
     *     <li>
     *         Run the application with the command : <b>server properties.yml</b>. Launch the application as an http server.
     *     </li>
     * </ul>
     *
     * @see <a href="https://dropwizard.github.io/dropwizard/manual/migrations.html">documentation</a>
     * @param args Arguments are not required to run this application
     * @throws Exception stops the application if an error occurred
     */
    public static void main(String[] args) throws Exception {
        // First, initialize the database
        new BookstoreApplication().run(new String[]{"db", "migrate", "properties.yml"});

        // Then run the jetty server
        new BookstoreApplication().run(new String[]{"server", "properties.yml"});
    }
}
