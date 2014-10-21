package fr.flst.jee.mmarie.instrumentation.interceptors;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Created by Maximilien on 18/10/2014.
 */
public class TimedInterceptor implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimedInterceptor.class);

    private MetricRegistry metricRegistry;

    public TimedInterceptor(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    public String chooseName(String explicitName, boolean absolute, Method method, String... suffixes) {
        if (explicitName != null && !explicitName.isEmpty()) {
            if (absolute) {
                return explicitName;
            }
            return name(method.getDeclaringClass().getName(), explicitName);
        }
        return name(name(method.getName()),
                suffixes);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        LOGGER.info("Method [{}] has been invoked", methodInvocation.getMethod());

        Timed annotation = methodInvocation.getMethod().getAnnotation(Timed.class);
        // If the metric does not exists, it will be created
        Timer timer = metricRegistry.timer(chooseName(annotation.name(), annotation.absolute(), methodInvocation.getMethod()));
        Timer.Context context = timer.time();
        Object proceed = methodInvocation.proceed();
        context.stop();
        return proceed;
    }
}
