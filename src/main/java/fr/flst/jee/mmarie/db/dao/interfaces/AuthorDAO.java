package fr.flst.jee.mmarie.db.dao.interfaces;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.Author;

/**
 * Created by Maximilien on 19/10/2014.
 */
public interface AuthorDAO {
    Optional<Author> findById(Integer id);
}
