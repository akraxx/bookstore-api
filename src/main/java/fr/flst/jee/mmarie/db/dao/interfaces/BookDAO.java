package fr.flst.jee.mmarie.db.dao.interfaces;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.Book;

import java.util.List;

/**
 * Created by Maximilien on 19/10/2014.
 */
public interface BookDAO {
    Optional<Book> findById(String isbn13);

    List<Book> findByAuthorId(Integer authorId);
}
