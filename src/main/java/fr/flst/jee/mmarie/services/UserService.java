package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.db.dao.interfaces.UserDAO;
import fr.flst.jee.mmarie.dto.UserDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Slf4j
public class UserService {
    private final UserDAO userDAO;

    private final DtoMappingService dtoMappingService;

    public User findSafely(String login) {
        final Optional<User> user = userDAO.findByLogin(login);
        if (!user.isPresent()) {
            throw new NotFoundException("No such user.");
        }
        return user.get();
    }

    @Inject
    public UserService(UserDAO userDAO, DtoMappingService dtoMappingService) {
        log.info("Initialize '{}'", getClass().getName());
        this.userDAO = userDAO;
        this.dtoMappingService = dtoMappingService;
    }

    public UserDto findByLogin(String login) {
        return dtoMappingService.convertsToDto(findSafely(login), UserDto.class);
    }

    public UserDto insert(User user) {
        if(userDAO.findByLogin(user.getLogin()).isPresent()) {
            throw new IllegalArgumentException(String.format("Username %s already exists", user.getLogin()));
        }
        return dtoMappingService.convertsToDto(userDAO.insert(user), UserDto.class);
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userDAO.findByUsernameAndPassword(username, password);
    }
}
