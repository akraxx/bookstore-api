package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.dto.AuthorDto;
import fr.flst.jee.mmarie.services.AuthorService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * {@link fr.flst.jee.mmarie.resources.api.AuthorResource} exposes the {@link fr.flst.jee.mmarie.core.Author}.
 */
@Path("/author")
@Api(value = "/author")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class AuthorResource {
    private AuthorService authorService;

    @Inject
    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * <p>
     *     Find an {@link fr.flst.jee.mmarie.dto.AuthorDto} by the {@code authorId}.
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @param authorId Id of the {@link fr.flst.jee.mmarie.core.Author}
     * @return The author.
     * @throws com.sun.jersey.api.NotFoundException if the author has not been found.
     */
    @GET
    @ApiOperation("Get the author by id")
    @Timed
    @Path("/{authorId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public AuthorDto findById(@Auth User user, @PathParam("authorId") IntParam authorId) {
        return authorService.findById(authorId);
    }
}
