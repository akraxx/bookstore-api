package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import fr.flst.jee.mmarie.core.AccessToken;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.dto.UserDto;
import fr.flst.jee.mmarie.services.AccessTokenService;
import fr.flst.jee.mmarie.services.UserService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Path("/user")
@Api("/user")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
@Slf4j
public class UserResource {
    private UserService userService;
    private AccessTokenService accessTokenService;

    @Inject
    public UserResource(UserService userService, AccessTokenService accessTokenService) {
        log.info("Initialize '{}'", getClass().getName());
        this.userService = userService;
        this.accessTokenService = accessTokenService;
    }

    @GET
    @ApiOperation("Get user by login")
    @Timed
    @Path("/{userLogin}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public UserDto findByLogin(@Auth User user, @PathParam("userLogin") String userLogin) {
        return userService.findByLogin(userLogin);
    }

    @POST
    @ApiOperation("Create a user")
    @Timed
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public UserDto insert(@ApiParam(value = "User login", required = true)
                       @Valid User user) {
        return userService.insert(user);
    }

    @POST
    @Path("/authenticate")
    @ApiOperation("Authenticate a user")
    @Timed
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    public String authorize(@FormParam("username") String username, @FormParam("password") String password) {
        // Try to find a user with the supplied credentials.
        log.info("Log user with username {} and password {}", username, password);
        Optional<User> user = userService.findByUsernameAndPassword(username, password);
        if (user == null || !user.isPresent()) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
        }

        // User was found, generate a token and return it.
        AccessToken accessToken = accessTokenService.generateNewAccessToken(user.get(), new DateTime());
        return accessToken.getAccessTokenId().toString();
    }




}
