package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import fr.flst.jee.mmarie.core.AccessToken;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.dto.UserDto;
import fr.flst.jee.mmarie.services.AccessTokenService;
import fr.flst.jee.mmarie.services.UserService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * {@link fr.flst.jee.mmarie.resources.api.UserResource} exposes the {@link fr.flst.jee.mmarie.core.User}.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
@Slf4j
public class UserResource {
    private UserService userService;
    private AccessTokenService accessTokenService;

    @Inject
    public UserResource(UserService userService, AccessTokenService accessTokenService) {
        this.userService = userService;
        this.accessTokenService = accessTokenService;
    }

    /**
     * <p>
     *     Find an {@link fr.flst.jee.mmarie.dto.UserDto} by the {@code userLogin}.
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @param userLogin Login of the {@link fr.flst.jee.mmarie.core.User}
     * @return The user.
     * @throws com.sun.jersey.api.NotFoundException if the author has not been found.
     */
    @GET
    @Timed
    @Path("/{userLogin}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public UserDto findByLogin(@Auth User user, @PathParam("userLogin") String userLogin) {
        return userService.findByLogin(userLogin);
    }

    /**
     * <p>
     *     Return the {@code user}.
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @return The logged user.
     */
    @GET
    @Timed
    @Path("/me")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public UserDto findByLogin(@Auth User user) {
        return userService.findMe(user);
    }

    /**
     * <p>
     *     Update the mail of the logged {@link fr.flst.jee.mmarie.core.User}
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @return The logged user.
     */
    @PUT
    @Timed
    @Path("/updateEmail")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public UserDto updateEmail(@Auth User user, @Valid @Email String email) {
        log.info("[{}] - Update email {}", user, email);

        return userService.updateEmail(user, email);
    }

    /**
     * <p>
     *     Update the password of the logged {@link fr.flst.jee.mmarie.core.User}
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @return The logged user.
     */
    @PUT
    @Timed
    @Path("/updatePassword")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public UserDto updatePassword(@Auth User user, @Valid @Size(min = 6, max = 20) String password) {
        log.info("[{}] - Update password {}", user, password);

        return userService.updatePassword(user, password);
    }

    /**
     * <p>
     *     Insert a {@link fr.flst.jee.mmarie.core.User}
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user {@link fr.flst.jee.mmarie.core.User} to insert
     * @return The inserted user.
     */
    @POST
    @Timed
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public UserDto insert(@Valid User user) {
        return userService.insert(user);
    }

    /**
     * <p>
     *     Authenticate a {@link fr.flst.jee.mmarie.core.User} by it's {@code username} and {@code password}
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param username Username of the {@link fr.flst.jee.mmarie.core.User}
     * @param password Password of the {@link fr.flst.jee.mmarie.core.User}
     * @return The token of the logged user.
     */
    @POST
    @Path("/authenticate")
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
