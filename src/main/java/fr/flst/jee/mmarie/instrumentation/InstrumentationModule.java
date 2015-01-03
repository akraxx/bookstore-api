package fr.flst.jee.mmarie.instrumentation;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import fr.flst.jee.mmarie.instrumentation.interceptors.TimedInterceptor;

/**
 * {@link fr.flst.jee.mmarie.instrumentation.InstrumentationModule} is useful to time methods and register it into
 * the dropwizard {@link com.codahale.metrics.MetricRegistry} available <a href="http://localhost:9091/">here</a>.
 */
public class InstrumentationModule extends AbstractModule {

    private MetricRegistry metricRegistry;

    public InstrumentationModule(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    /**
     * Intercept {@code any} method {@code annotatedWith} {@link com.codahale.metrics.annotation.Timed} with a
     * {@link fr.flst.jee.mmarie.instrumentation.interceptors.TimedInterceptor}.
     */
    @Override
    protected void configure() {
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Timed.class),
                new TimedInterceptor(metricRegistry));
    }
}
