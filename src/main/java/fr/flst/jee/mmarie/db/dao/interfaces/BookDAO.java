package fr.flst.jee.mmarie.db.dao.interfaces;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.Book;

import java.util.List;

/**
 * {@link fr.flst.jee.mmarie.db.dao.interfaces.BookDAO} defines which queries are available
 * to manipulate an {@link fr.flst.jee.mmarie.core.Book}
 */
public interface BookDAO {

    /**
     * Find an {@link fr.flst.jee.mmarie.core.Author} by it's {@code isbn13}.
     *
     * @param isbn13 Isbn13 of the book.
     * @return The book if it exists, {@link com.google.common.base.Optional#absent()} otherwise.
     */
    Optional<Book> findById(String isbn13);

    /**
     * Find all {@link fr.flst.jee.mmarie.core.Author} by {@code authorId}.
     *
     * @param authorId Id of the author.
     * @return List of books which have the author with id {@code authorId}.
     */
    List<Book> findByAuthorId(Integer authorId);

    /**
     * Find all {@link fr.flst.jee.mmarie.core.Book}
     *
     * @return List of all books
     */
    List<Book> findAll();
}
