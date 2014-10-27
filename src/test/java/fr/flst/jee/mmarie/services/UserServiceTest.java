package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.db.dao.interfaces.UserDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
public class UserServiceTest {

    private static final UserDAO userDAO = mock(UserDAO.class);

    private User user1 = User.builder()
            .login("login")
            .email("test@test.eu")
            .password("pwd")
            .build();

    private UserService userService = new UserService(userDAO);

    @Before
    public void setup() {
        when(userDAO.findByLogin("login")).thenReturn(Optional.of(user1));
        when(userDAO.findByLogin("notExisting")).thenReturn(Optional.absent());
    }

    @After
    public void tearDown() {
        reset(userDAO);
    }

    @Test
    public void testGetUser() {
        assertThat(userService.findByLogin("login"),
                is(user1));
        verify(userDAO).findByLogin("login");
    }

    @Test(expected = NotFoundException.class)
    public void testGetMailingAddressNotExisting() {
        userService.findByLogin("notExisting");
    }
}
