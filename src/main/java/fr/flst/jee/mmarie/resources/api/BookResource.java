package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.dto.BookDto;
import fr.flst.jee.mmarie.services.BookService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * {@link fr.flst.jee.mmarie.resources.api.BookResource} exposes the {@link fr.flst.jee.mmarie.core.Book}.
 */
@Path("/book")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class BookResource {
    private BookService bookService;

    @Inject
    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * <p>
     *     Find all {@link fr.flst.jee.mmarie.dto.BookDto}.
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @return Each book found in the database
     */
    @GET
    @Timed
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public List<BookDto> findAll(@Auth User user) {
        return bookService.findAll();
    }

    /**
     * <p>
     *     Find an {@link fr.flst.jee.mmarie.dto.BookDto} by the {@code bookIsbn13}.
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @param bookIsbn13 Isbn13 of the {@link fr.flst.jee.mmarie.core.Book}
     * @return The book.
     * @throws com.sun.jersey.api.NotFoundException if the author has not been found.
     */
    @GET
    @Timed
    @Path("/{bookIsbn13}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public BookDto findById(@Auth User user, @PathParam("bookIsbn13") String bookIsbn13) {
        return bookService.findById(bookIsbn13);
    }

    /**
     * <p>
     *     Find a list of {@link fr.flst.jee.mmarie.dto.BookDto} by the {@code authorId}.
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @param authorId Id of the {@link fr.flst.jee.mmarie.core.Author}
     * @return List of books
     */
    @GET
    @Timed
    @Path("/byAuthor/{authorId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public List<BookDto> findByAuthorId(@Auth User user, @PathParam("authorId") IntParam authorId) {
        return bookService.findByAuthorId(authorId);
    }

}
