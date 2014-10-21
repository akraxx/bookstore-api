package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.services.UserService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
public class UserResource {
    private UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Timed
    @Path("{userLogin}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
    public User findByLogin(@PathParam("userLogin") String userLogin) {
        return userService.findByLogin(userLogin);
    }
}
