package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.db.dao.interfaces.UserDAO;
import fr.flst.jee.mmarie.dto.UserDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maximilien on 24/10/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private DtoMappingService dtoMappingService;

    private User user1 = User.builder()
            .login("login")
            .email("test@test.eu")
            .password("pwd")
            .build();

    private UserDto userDto1 = UserDto.builder()
            .login("login")
            .email("test@test.eu")
            .password("pwd")
            .build();

    private UserService userService;

    @Before
    public void setup() {
        when(userDAO.findByLogin("login")).thenReturn(Optional.of(user1));
        when(userDAO.findByLogin("notExisting")).thenReturn(Optional.absent());

        when(dtoMappingService.convertsToDto(user1, UserDto.class)).thenReturn(userDto1);

        userService = new UserService(userDAO, dtoMappingService);
    }

    @After
    public void tearDown() {
        reset(userDAO);
    }

    @Test
    public void testGetUser() {
        assertThat(userService.findByLogin("login"),
                is(userDto1));
        verify(userDAO).findByLogin("login");
    }

    @Test(expected = NotFoundException.class)
    public void testGetMailingAddressNotExisting() {
        userService.findByLogin("notExisting");
    }
}
