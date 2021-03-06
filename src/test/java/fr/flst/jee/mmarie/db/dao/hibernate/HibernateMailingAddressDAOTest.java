package fr.flst.jee.mmarie.db.dao.hibernate;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.MailingAddress;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HibernateMailingAddressDAOTest extends AbstractDAOTestCase {

    private HibernateMailingAddressDAO hibernateMailingAddressDAO;

    @Before
    public void setUp() throws Exception {
        hibernateMailingAddressDAO = new HibernateMailingAddressDAO(sessionFactory);
    }

    @Test
    public void testFindById() throws Exception {
        Optional<MailingAddress> addressOptional = hibernateMailingAddressDAO.findById(1);
        assertThat(addressOptional.isPresent(), is(true));
        assertThat(addressOptional.get(), allOf(hasProperty("zip", is("59000")),
                hasProperty("city", is("Lille")),
                hasProperty("line1", is("41 bd vauban"))));

    }

    @Test
    public void testPersist() throws Exception {
        MailingAddress paris = MailingAddress.builder()
                .zip("75000")
                .city("Paris")
                .line1("1 av Champs Elysées")
                .build();
        hibernateMailingAddressDAO.persist(paris);

        assertThat(paris.getId(), is(4));
    }
}