package fr.flst.jee.mmarie.misc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 * Misc tools for the application.
 */
public class MiscModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    /**
     * Provides a {@link com.google.inject.Singleton} of {@link org.dozer.DozerBeanMapper}
     *
     * @return An instance of {@link org.dozer.DozerBeanMapper}
     */
    @Provides
    @Singleton
    public Mapper providesMapper() {
        return new DozerBeanMapper();
    }
}
