package fr.flst.jee.mmarie.db.dao.interfaces;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.User;

/**
 * Created by Maximilien on 21/10/2014.
 */
public interface UserDAO {
    Optional<User> findByLogin(String login);
}
