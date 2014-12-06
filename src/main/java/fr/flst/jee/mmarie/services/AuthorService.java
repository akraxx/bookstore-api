package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.db.dao.interfaces.AuthorDAO;
import fr.flst.jee.mmarie.dto.AuthorDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import io.dropwizard.jersey.params.IntParam;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class AuthorService {
    private final AuthorDAO authorDAO;

    private final DtoMappingService dtoMappingService;

    public Author findSafely(Integer id) {
        final Optional<Author> author = authorDAO.findById(id);
        if (!author.isPresent()) {
            throw new NotFoundException("No such author.");
        }
        return author.get();
    }

    @Inject
    public AuthorService(AuthorDAO authorDAO, DtoMappingService dtoMappingService) {
        this.authorDAO = authorDAO;
        this.dtoMappingService = dtoMappingService;
    }

    public AuthorDto findById(IntParam authorId) {
        return dtoMappingService.convertsToDto(findSafely(authorId.get()), AuthorDto.class);
    }
}
