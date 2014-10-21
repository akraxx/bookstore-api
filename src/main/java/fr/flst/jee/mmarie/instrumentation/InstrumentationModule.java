package fr.flst.jee.mmarie.instrumentation;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import fr.flst.jee.mmarie.instrumentation.interceptors.TimedInterceptor;

/**
 * Created by Maximilien on 18/10/2014.
 */
public class InstrumentationModule extends AbstractModule {

    private MetricRegistry metricRegistry;

    public InstrumentationModule(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Override
    protected void configure() {
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Timed.class),
                new TimedInterceptor(metricRegistry));
    }
}
