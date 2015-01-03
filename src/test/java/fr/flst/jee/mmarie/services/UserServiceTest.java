package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.MailingAddress;
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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
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

    @Mock
    private AccessTokenService accessTokenService;

    private User user1 = spy(User.builder()
            .login("login")
            .email("test@test.eu")
            .password("pwd")
            .build());

    private User notExisting = User.builder()
            .login("notExisting")
            .email("not@present.fr")
            .password("pwd2")
            .build();

    private UserDto userDto1 = UserDto.builder()
            .login("login")
            .email("test@test.eu")
            .password("pwd")
            .build();

    private UserDto notExistingDto = UserDto.builder()
            .login("notExisting")
            .email("not@present.fr")
            .password("pwd2")
            .build();

    private UserService userService;

    @Before
    public void setup() {
        when(userDAO.findByLogin("login")).thenReturn(Optional.of(user1));
        when(userDAO.findByLogin("notExisting")).thenReturn(Optional.absent());
        when(userDAO.findByLoginAndPassword("username", "password")).thenReturn(Optional.of(user1));
        when(userDAO.persist(user1)).thenReturn(user1);
        when(userDAO.persist(notExisting)).thenReturn(notExisting);

        when(dtoMappingService.convertsToDto(user1, UserDto.class)).thenReturn(userDto1);
        when(dtoMappingService.convertsToDto(notExisting, UserDto.class)).thenReturn(notExistingDto);

        userService = new UserService(userDAO, dtoMappingService, accessTokenService);
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

    @Test(expected = IllegalArgumentException.class)
    public void testInsert_Already_Present_User() {
        assertThat(userService.insert(user1), is(userDto1));
    }

    @Test
    public void testInsert_Absent_User() {
        assertThat(userService.insert(notExisting), is(notExistingDto));
    }

    @Test
    public void testUpdateEmail() {
        String email = "email@updated.fr";
        userService.updateEmail(user1, email);

        verify(user1, times(1)).setEmail(email);
        verify(accessTokenService, times(1)).updatedUser(user1);
    }

    @Test
    public void testUpdatePassword() {
        String pwd = "updatedpwd";
        userService.updatePassword(user1, pwd);

        verify(user1, times(1)).setPassword(pwd);
        verify(accessTokenService, times(1)).updatedUser(user1);
    }

    @Test
    public void testUpdateMailingAddress() {
        MailingAddress mailingAddress = MailingAddress.builder().id(1).build();
        userService.updateMailingAddress(user1, mailingAddress);

        verify(user1, times(1)).setMailingAddress(mailingAddress);
        verify(accessTokenService, times(1)).updatedUser(user1);
    }

    @Test
    public void testFindByUsernameAndPassword() {
        assertThat(userService.findByUsernameAndPassword("username", "password").get(), is(user1));

        verify(userDAO, times(1)).findByLoginAndPassword("username", "password");
    }

    @Test
    public void testFindMe() {
        assertThat(userService.findMe(user1), is(userDto1));
    }
}
