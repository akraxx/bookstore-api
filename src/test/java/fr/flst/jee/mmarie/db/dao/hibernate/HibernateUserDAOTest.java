package fr.flst.jee.mmarie.db.dao.hibernate;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HibernateUserDAOTest extends AbstractDAOTestCase {

    private HibernateUserDAO hibernateUserDAO;

    @Before
    public void setUp() throws Exception {
        hibernateUserDAO = new HibernateUserDAO(sessionFactory);
    }

    @Test
    public void testFindByLogin() throws Exception {
        Optional<User> yhovart = hibernateUserDAO.findByLogin("yhovart");

        assertThat(yhovart.isPresent(), is(true));
        assertThat(yhovart.get(), allOf(
                        hasProperty("login", is("yhovart")),
                        hasProperty("password", is("yhovartpwd")),
                        hasProperty("email", is("yhovart@gmail.com")),
                        hasProperty("mailingAddress",
                                allOf(
                                        hasProperty("zip", is("59000")),
                                        hasProperty("city", is("Lille")),
                                        hasProperty("line1", is("41 bd vauban"))
                                )
                        ),
                        hasProperty("orders", hasSize(2))
                )
        );
    }

    @Test
    public void testPersist() throws Exception {
        User user = User.builder()
                .login("worldline")
                .password("worldline")
                .email("mail@worldline.com")
                .build();

        hibernateUserDAO.persist(user);

        Optional<User> worldline = hibernateUserDAO.findByLogin("worldline");

        assertThat(worldline.isPresent(), is(true));
        assertThat(worldline.get(), is(user));
    }

    @Test
    public void testFindByUsernameAndPassword() throws Exception {
        Optional<User> byUsernameAndPassword = hibernateUserDAO.findByUsernameAndPassword("yhovart", "yhovartpwd");

        assertThat(byUsernameAndPassword.isPresent(), is(true));
        assertThat(byUsernameAndPassword.get(), allOf(
                        hasProperty("login", is("yhovart")),
                        hasProperty("password", is("yhovartpwd")),
                        hasProperty("email", is("yhovart@gmail.com")),
                        hasProperty("mailingAddress",
                                allOf(
                                        hasProperty("zip", is("59000")),
                                        hasProperty("city", is("Lille")),
                                        hasProperty("line1", is("41 bd vauban"))
                                )
                        ),
                        hasProperty("orders", hasSize(2))
                )
        );
    }
}