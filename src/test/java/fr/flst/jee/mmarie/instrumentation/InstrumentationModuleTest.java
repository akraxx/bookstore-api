package fr.flst.jee.mmarie.instrumentation;

import com.codahale.metrics.MetricRegistry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class InstrumentationModuleTest {

    @Mock
    private MetricRegistry metricRegistry;

    private InstrumentationModule instrumentationModule;

    @Before
    public void setUp() throws Exception {
        instrumentationModule = new InstrumentationModule(metricRegistry);
    }

    @Test
    public void testConfigure() throws Exception {

    }

}