package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import fr.flst.jee.mmarie.core.Author;
import fr.flst.jee.mmarie.services.AuthorService;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Path("/api/author")
public class AuthorResource {
    private AuthorService authorService;

    @Inject
    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GET
    @Timed
    @Path("{authorId}")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
    public Author findById(@PathParam("authorId") IntParam authorId) {
        return authorService.findById(authorId);
    }
}
