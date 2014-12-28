package fr.flst.jee.mmarie.db.dao.hibernate;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.Author;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HibernateAuthorDAOTest extends AbstractTestCase {

    private HibernateAuthorDAO hibernateAuthorDAO;

    @Before
    public void setUp() throws Exception {
        System.out.println(sessionFactory.toString());
        hibernateAuthorDAO = new HibernateAuthorDAO(sessionFactory);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFindById() throws Exception {
        Optional<Author> authorOptional = hibernateAuthorDAO.findById(1);
        assertThat(authorOptional.isPresent(), is(true));
        assertThat(authorOptional.get().getFirstName(), is("Merick"));
        assertThat(authorOptional.get().getLastName(), is("Schincariol"));
        assertThat(authorOptional.get().getBirthDate().toString(), containsString("1981-12-28"));
        assertThat(authorOptional.get().getBooks().size(), is(1));
        assertThat(authorOptional.get().getBooks().get(0).getIsbn13(), is("978-1932394887"));
    }
}