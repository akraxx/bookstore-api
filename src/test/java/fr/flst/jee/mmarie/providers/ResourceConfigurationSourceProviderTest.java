package fr.flst.jee.mmarie.providers;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ResourceConfigurationSourceProviderTest {

    private ResourceConfigurationSourceProvider provider = new ResourceConfigurationSourceProvider();

    @Test
    public void testOpen() throws Exception {
        InputStream stream = provider.open("test.yml");
        assertThat(IOUtils.toString(stream, Charset.forName("UTF-8")), is("this is my content file!"));
    }


}