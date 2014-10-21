package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.db.dao.interfaces.AuthorDAO;
import fr.flst.jee.mmarie.db.dao.interfaces.BookDAO;
import io.dropwizard.jersey.params.IntParam;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class AuthorService {
    private AuthorDAO authorDAO;

    public Author findSafely(Integer id) {
        final Optional<Author> author = authorDAO.findById(id);
        if (!author.isPresent()) {
            throw new NotFoundException("No such author.");
        }
        return author.get();
    }

    @Inject
    public AuthorService(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    public Author getAuthorById(IntParam authorId) {
        return findSafely(authorId.get());
    }
}
