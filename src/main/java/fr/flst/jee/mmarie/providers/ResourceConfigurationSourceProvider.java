package fr.flst.jee.mmarie.providers;

import io.dropwizard.configuration.ConfigurationSourceProvider;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link fr.flst.jee.mmarie.providers.ResourceConfigurationSourceProvider} is a custom {@link io.dropwizard.configuration.ConfigurationSourceProvider}.
 */
public class ResourceConfigurationSourceProvider implements ConfigurationSourceProvider {

    /**
     * Retrieve the configuration {@code path} from the classpath.
     *
     * @param path Configuration file name
     * @return The configuration as string.
     * @throws IOException if there is an error with the configuration file.
     */
    @Override
    public InputStream open(String path) throws IOException {
        return checkNotNull(ResourceConfigurationSourceProvider.class.getResourceAsStream("/".concat(path)));
    }
}
