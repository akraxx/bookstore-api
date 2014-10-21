package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.db.dao.interfaces.UserDAO;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class UserService {
    private UserDAO userDAO;

    public User findSafely(String login) {
        final Optional<User> user = userDAO.findByLogin(login);
        if (!user.isPresent()) {
            throw new NotFoundException("No such user.");
        }
        return user.get();
    }

    @Inject
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findByLogin(String login) {
        return findSafely(login);
    }
}
