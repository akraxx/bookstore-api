package fr.flst.jee.mmarie.db.dao.hibernate;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.MailingAddress;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

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