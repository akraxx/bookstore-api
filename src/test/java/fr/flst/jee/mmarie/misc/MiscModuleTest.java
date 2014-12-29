package fr.flst.jee.mmarie.misc;

import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MiscModuleTest {

    private MiscModule miscModule = new MiscModule();

    @Test
    public void testConfigure() throws Exception {

    }

    @Test
    public void testProvidesMapper() throws Exception {
        assertThat(miscModule.providesMapper(), is(notNullValue()));
    }
}