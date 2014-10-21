package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import fr.flst.jee.mmarie.core.Book;
import fr.flst.jee.mmarie.services.BookService;
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
@Path("/api/book")
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
public class BookResource {
    private BookService bookService;

    @Inject
    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @Timed
    @Path("{bookIsbn13}")
    @UnitOfWork
    public Book findById(@PathParam("bookIsbn13") String bookIsbn13) {
        return bookService.findById(bookIsbn13);
    }

    @GET
    @Timed
    @Path("/byAuthor/{authorId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
    public List<Book> findByAuthorId(@PathParam("authorId") IntParam authorId) {
        return bookService.findByAuthorId(authorId);
    }

}
