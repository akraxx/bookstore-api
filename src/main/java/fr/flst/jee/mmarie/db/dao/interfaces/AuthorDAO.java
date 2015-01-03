package fr.flst.jee.mmarie.db.dao.interfaces;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.Author;

/**
 * {@link fr.flst.jee.mmarie.db.dao.interfaces.AuthorDAO} defines which queries are available
 * to manipulate an {@link fr.flst.jee.mmarie.core.Author}
 */
public interface AuthorDAO {

    /**
     * Find an {@link fr.flst.jee.mmarie.core.Author} by it's {@code id}.
     *
     * @param id Id of the author.
     * @return The author if it exists, {@link com.google.common.base.Optional#absent()} otherwise.
     */
    Optional<Author> findById(Integer id);
}
