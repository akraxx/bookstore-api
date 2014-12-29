package fr.flst.jee.mmarie.instrumentation;

import com.codahale.metrics.MetricRegistry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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