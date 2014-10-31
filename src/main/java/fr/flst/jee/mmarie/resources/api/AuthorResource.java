package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
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
@Api(value = "/api/author")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class AuthorResource {
    private AuthorService authorService;

    @Inject
    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GET
    @ApiOperation("Get the author by id")
    @Timed
    @Path("/{authorId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Author findById(@PathParam("authorId") IntParam authorId) {
        return authorService.findById(authorId);
    }
}
