package fr.flst.jee.mmarie.providers;

import io.dropwizard.configuration.ConfigurationSourceProvider;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maximilien on 07/11/2014.
 */
public class ResourceConfigurationSourceProvider implements ConfigurationSourceProvider {
    @Override
    public InputStream open(String path) throws IOException {
        return checkNotNull(ResourceConfigurationSourceProvider.class.getResourceAsStream("/".concat(path)));
    }
}
