package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
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
 * Created by Maximilien on 19/10/2014.
 */
@Path("/book")
@Api("/book")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class BookResource {
    private BookService bookService;

    @Inject
    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @ApiOperation("Get all books")
    @Timed
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public List<BookDto> findAll(@Auth User user) {
        return bookService.findAll();
    }

    @GET
    @ApiOperation("Get a book by isbn13")
    @Timed
    @Path("/{bookIsbn13}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public BookDto findById(@Auth User user, @PathParam("bookIsbn13") String bookIsbn13) {
        return bookService.findById(bookIsbn13);
    }

    @GET
    @ApiOperation("Get books by author id")
    @Timed
    @Path("/byAuthor/{authorId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public List<BookDto> findByAuthorId(@Auth User user, @PathParam("authorId") IntParam authorId) {
        return bookService.findByAuthorId(authorId);
    }

}
