package fr.flst.jee.mmarie.db.modules;

import fr.flst.jee.mmarie.BookstoreConfiguration;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HibernateModuleTest {

    @Mock
    private HibernateBundle<BookstoreConfiguration> hibernateBundle;

    @Mock
    private Bootstrap<BookstoreConfiguration> bootstrap;

    @Mock
    private SessionFactory sessionFactory;

    private HibernateModule hibernateModule;

    @Before
    public void setUp() throws Exception {
        hibernateModule = spy(new HibernateModule(bootstrap, hibernateBundle));

        when(hibernateBundle.getSessionFactory()).thenReturn(sessionFactory);
    }

    @Test
    public void testConfigure() throws Exception {
        verify(bootstrap, times(1)).addBundle(hibernateBundle);
    }

    @Test
    public void testProvideSessionFactory() throws Exception {
        assertThat(hibernateModule.provideSessionFactory(), is(sessionFactory));
    }
}