package fr.flst.jee.mmarie.db.dao.hibernate;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.Order;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HibernateOrderDAOTest extends AbstractDAOTestCase {

    private HibernateOrderDAO hibernateOrderDAO;

    @Before
    public void setUp() throws Exception {
        hibernateOrderDAO = new HibernateOrderDAO(sessionFactory);
    }

    @Test
    public void testFindById() throws Exception {
        Optional<Order> orderOptional = hibernateOrderDAO.findById(1);
        assertThat(orderOptional.isPresent(), is(true));
        assertThat(orderOptional.get(), allOf(hasProperty("user", hasProperty("login", is("yhovart"))),
                hasProperty("orderDate", comparesEqualTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-12-26 14:15:00"))),
                hasProperty("mailingAddress", hasProperty("id", is(2)))));
    }
}