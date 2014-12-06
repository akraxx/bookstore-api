package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.db.dao.interfaces.BookDAO;
import fr.flst.jee.mmarie.dto.BookDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import io.dropwizard.jersey.params.IntParam;

import java.util.List;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class BookService {
    private final BookDAO bookDAO;

    private final DtoMappingService dtoMappingService;

    public Book findSafely(String isbn13) {
        final Optional<Book> book = bookDAO.findById(isbn13);
        if (!book.isPresent()) {
            throw new NotFoundException("No such book.");
        }
        return book.get();
    }

    @Inject
    public BookService(BookDAO bookDAO, DtoMappingService dtoMappingService) {
        this.bookDAO = bookDAO;
        this.dtoMappingService = dtoMappingService;
    }

    public BookDto findById(String isbn13) {
        return dtoMappingService.convertsToDto(findSafely(isbn13), BookDto.class);
    }

    public List<BookDto> findByAuthorId(IntParam authorId) {
        return dtoMappingService.convertsListToDto(bookDAO.findByAuthorId(authorId.get()), BookDto.class);
    }

    public List<BookDto> findAll() {
        return dtoMappingService.convertsListToDto(bookDAO.findAll(), BookDto.class);
    }
}
