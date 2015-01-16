package fr.flst.jee.mmarie.db.dao.hibernate;

import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.core.OrderLine;
import fr.flst.jee.mmarie.core.OrderLineId;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HibernateOrderLineDAOTest extends AbstractDAOTestCase {

    private HibernateOrderLineDAO hibernateOrderLineDAO;

    @Before
    public void setUp() throws Exception {
        hibernateOrderLineDAO = new HibernateOrderLineDAO(sessionFactory);
    }

    @Test
    public void testFindByBookIsbn13() throws Exception {
        List<OrderLine> byBookIsbn13 = hibernateOrderLineDAO.findByBookIsbn13("978-1430219569");
        assertThat(byBookIsbn13, hasItems(
                        allOf(
                                Matchers.<OrderLine>hasProperty("book", hasProperty("isbn13", is("978-1430219569"))),
                                Matchers.<OrderLine>hasProperty("order", hasProperty("id", is(1))),
                                Matchers.<OrderLine>hasProperty("quantity", is(2))
                        ),
                        allOf(
                                Matchers.<OrderLine>hasProperty("book", hasProperty("isbn13", is("978-1430219569"))),
                                Matchers.<OrderLine>hasProperty("order", hasProperty("id", is(2))),
                                Matchers.<OrderLine>hasProperty("quantity", is(5))
                        )
                )
        );
    }

    @Test
    public void testFindByOrderId() throws Exception {
        List<OrderLine> byOrderId = hibernateOrderLineDAO.findByOrderId(2);
        assertThat(byOrderId, hasItems(
                        allOf(
                                Matchers.<OrderLine>hasProperty("book", hasProperty("isbn13", is("978-2070415731"))),
                                Matchers.<OrderLine>hasProperty("order", hasProperty("id", is(2))),
                                Matchers.<OrderLine>hasProperty("quantity", is(1))
                        ),
                        allOf(
                                Matchers.<OrderLine>hasProperty("book", hasProperty("isbn13", is("978-1430219569"))),
                                Matchers.<OrderLine>hasProperty("order", hasProperty("id", is(2))),
                                Matchers.<OrderLine>hasProperty("quantity", is(5))
                        )
                )
        );
    }

    @Test
    public void testPersist() throws Exception {
        Order order1 = Order.builder().orderDate(new Date()).build();
        em.persist(order1);
        Book isbn13 = Book.builder().isbn13("ISBN13").title("title").unitPrice(50.2).build();
        em.persist(isbn13);
        OrderLine order = OrderLine.builder()
                .quantity(1)
                .pk(new OrderLineId(order1, isbn13))
                .build();

        assertThat(hibernateOrderLineDAO.persist(order), is(notNullValue()));
    }
}