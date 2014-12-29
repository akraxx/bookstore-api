package fr.flst.jee.mmarie.instrumentation.interceptors;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Method;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimedInterceptorTest {

    @Mock
    private MetricRegistry metricRegistry;

    @Mock
    private MethodInvocation methodInvocation;

    @Mock
    private Timer timer;

    @Mock
    private Timer.Context context;

    private Method method;

    private TimedInterceptor timedInterceptor;

    @Timed
    public void exampleMethod() {

    }

    @Before
    public void setUp() throws Exception {
        timedInterceptor = new TimedInterceptor(metricRegistry);

        method = getClass().getMethod("exampleMethod");

        when(methodInvocation.getMethod()).thenReturn(method);
        when(metricRegistry.timer("exampleMethod")).thenReturn(timer);
        when(timer.time()).thenReturn(context);
    }

    @After
    public void tearDown() throws Exception {
        reset(metricRegistry, methodInvocation, timer);
    }

    @Test
    public void testChooseName_Explicit_Null() throws Exception {
        String explicitName = null;
        boolean absolute = true;

        assertThat(timedInterceptor.chooseName(explicitName, absolute, method), is("exampleMethod"));
    }

    @Test
    public void testChooseName_Explicit_Empty() throws Exception {
        String explicitName = "";
        boolean absolute = true;

        assertThat(timedInterceptor.chooseName(explicitName, absolute, method), is("exampleMethod"));
    }

    @Test
    public void testChooseName_Explicit_Absolute() throws Exception {
        String explicitName = "explicit";
        boolean absolute = true;

        assertThat(timedInterceptor.chooseName(explicitName, absolute, method), is("explicit"));
    }

    @Test
    public void testChooseName_Explicit_NonAbsolute() throws Exception {
        String explicitName = "explicit";
        boolean absolute = false;

        assertThat(timedInterceptor.chooseName(explicitName, absolute, method),
                is("fr.flst.jee.mmarie.instrumentation.interceptors.TimedInterceptorTest.explicit"));
    }

    @Test
    public void testInvoke() throws Throwable {
        timedInterceptor.invoke(methodInvocation);

        verify(metricRegistry, times(1)).timer("exampleMethod");
        verify(timer, times(1)).time();
        verify(context, times(1)).stop();
    }
}