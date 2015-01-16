package fr.flst.jee.mmarie.misc;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.Map;

/**
 * Misc tools for the application.
 */
public class MiscModule extends AbstractModule {

    public static final String BOOK_CRITERIAS = "bookCriterias";

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

    @Provides
    @Singleton
    @Named(MiscModule.BOOK_CRITERIAS)
    public Map<String, String> providesBookCriterias() {
        return ImmutableMap.of("title", "Title",
                "isbn13", "ISBN13",
                "author.firstName", "Author's First Name",
                "author.lastName", "Author's Last Name",
                "editor", "Editor");
    }
}
