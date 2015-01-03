package fr.flst.jee.mmarie.governator;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.InjectorFactory;
import com.netflix.governator.guice.LifecycleInjector;

import java.util.List;

/**
 * <p>
 *     An {@link com.hubspot.dropwizard.guice.InjectorFactory} which use <a href="https://github.com/Netflix/governator">Governator</a>.
 * </p>
 *
 * <p>
 *     Governator provides the {@link com.netflix.governator.guice.lazy.LazySingleton} scope. Very useful to avoid initialization error
 *     when using a {@link com.google.inject.Singleton} on a object which needs the {@link io.dropwizard.Configuration} or the
 *     {@link io.dropwizard.setup.Environment}.
 * </p>
 *
 * @see <a href="https://github.com/akraxx/dropwizard-guice-governator">Why using governator with guice?</a>
 */
public class GovernatorInjectorFactory implements InjectorFactory {
    @Override
    public Injector create(Stage stage, List<Module> modules) {
        return LifecycleInjector.builder()
                .inStage(stage)
                .withModules(modules)
                .build()
                .createInjector(modules);
    }
}
