package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.db.dao.interfaces.BookDAO;
import io.dropwizard.jersey.params.IntParam;

import java.util.List;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class BookService {
    private BookDAO bookDAO;

    public Book findSafely(String isbn13) {
        final Optional<Book> book = bookDAO.findById(isbn13);
        if (!book.isPresent()) {
            throw new NotFoundException("No such book.");
        }
        return book.get();
    }

    @Inject
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public Book findById(String isbn13) {
        return findSafely(isbn13);
    }

    public List<Book> findByAuthorId(IntParam authorId) {
        return bookDAO.findByAuthorId(authorId.get());
    }
}
