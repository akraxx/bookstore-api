package fr.flst.jee.mmarie.db.dao.hibernate;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.Book;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HibernateBookDAOTest extends AbstractDAOTestCase {

    private HibernateBookDAO hibernateBookDAO;

    @Before
    public void setUp() throws Exception {
        hibernateBookDAO = new HibernateBookDAO(sessionFactory);
    }

    @Test
    public void testFindById() throws Exception {
        Optional<Book> bookOptional = hibernateBookDAO.findById("978-1932394887");
        assertThat(bookOptional.isPresent(), is(true));
        assertThat(bookOptional.get().getTitle(), is("Java Persistence with Hibernate"));
        assertThat(bookOptional.get().getUnitPrice(), is(47.03));
        assertThat(bookOptional.get().getEditor(), is(nullValue()));
        assertThat(bookOptional.get().getAuthor(), is(notNullValue()));
        assertThat(bookOptional.get().getAuthor().getFirstName(), is("Merick"));
    }

    @Test
    public void testFindByAuthorId() throws Exception {
        List<Book> byAuthorId = hibernateBookDAO.findByAuthorId(2);
        assertThat(byAuthorId.size(), is(1));
        assertThat(byAuthorId.get(0).getIsbn13(), is("978-1430219569"));
        assertThat(byAuthorId.get(0).getAuthor().getFirstName(), is("Mike"));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Book> books = hibernateBookDAO.findAll();
        assertThat(books.size(), is(3));
        assertThat(books, hasItems(Matchers.<Book>hasProperty("isbn13", equalTo("978-1430219569")),
                Matchers.<Book>hasProperty("isbn13", equalTo("978-1932394887")),
                Matchers.<Book>hasProperty("isbn13", equalTo("978-2070415731"))));
    }
}