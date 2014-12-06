package fr.flst.jee.mmarie.misc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 * Created by Maximilien on 06/12/2014.
 */
public class MiscModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public Mapper providesMapper() {
        return new DozerBeanMapper();
    }
}
