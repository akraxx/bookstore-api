package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.db.dao.interfaces.BookDAO;
import fr.flst.jee.mmarie.dto.BookDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import io.dropwizard.jersey.params.IntParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class BookService {
    private final BookDAO bookDAO;

    private final DtoMappingService dtoMappingService;

    private final Map<String, String> criterias;

    public Book findSafely(String isbn13) {
        final Optional<Book> book = bookDAO.findById(isbn13);
        if (!book.isPresent()) {
            throw new NotFoundException("No such book.");
        }
        return book.get();
    }

    @Inject
    public BookService(BookDAO bookDAO,
                       DtoMappingService dtoMappingService,
                       @Named("bookCriterias") Map<String, String> criterias) {
        this.bookDAO = bookDAO;
        this.dtoMappingService = dtoMappingService;
        this.criterias = criterias;
    }

    public BookDto findById(String isbn13) {
        return dtoMappingService.convertsToDto(findSafely(isbn13), BookDto.class);
    }

    public List<BookDto> findByAuthorId(IntParam authorId) {
        return dtoMappingService.convertsListToDto(bookDAO.findByAuthorId(authorId.get()), BookDto.class);
    }

    public List<BookDto> findByCriteriasLike(Map<String, String> criterias) {
        Set<String> availableCriterias = this.criterias.keySet();
        for (Map.Entry<String, String> criteriaEntry : criterias.entrySet()) {
            if(!availableCriterias.contains(criteriaEntry.getKey())) {
                throw new IllegalArgumentException("Unrecognized criteria : " + criteriaEntry.getKey());
            }
        }
        return dtoMappingService.convertsListToDto(bookDAO.findByCriteriasLike(criterias), BookDto.class);
    }

    public List<BookDto> findAll() {
        return dtoMappingService.convertsListToDto(bookDAO.findAll(), BookDto.class);
    }

    public Map<String, String> availableCriterias() {
        return criterias;
    }
}
