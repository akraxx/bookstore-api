package fr.flst.jee.mmarie.db.dao.interfaces;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.User;

/**
 * {@link fr.flst.jee.mmarie.db.dao.interfaces.UserDAO} defines which queries are available
 * to manipulate an {@link fr.flst.jee.mmarie.core.User}
 */
public interface UserDAO {
    /**
     * Find an {@link fr.flst.jee.mmarie.core.User} by it's {@code login}.
     *
     * @param login Login of the user.
     * @return The user if it exists, {@link com.google.common.base.Optional#absent()} otherwise.
     */
    Optional<User> findByLogin(String login);

    /**
     * Persist the {code user} into the database.
     *
     * @param user The {@link fr.flst.jee.mmarie.core.User} to persist.
     * @return The persisted instance.
     */
    User persist(User user);

    /**
     * Find an {@link fr.flst.jee.mmarie.core.User} by it's {@code login}.
     *
     * @param login Login of the user.
     * @return The user if it exists, {@link com.google.common.base.Optional#absent()} otherwise.
     */
    Optional<User> findByLoginAndPassword(String username, String password);
}
