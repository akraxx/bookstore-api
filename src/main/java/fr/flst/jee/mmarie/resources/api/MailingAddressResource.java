package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.dto.MailingAddressDto;
import fr.flst.jee.mmarie.services.MailingAddressService;
import fr.flst.jee.mmarie.services.UserService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * {@link fr.flst.jee.mmarie.resources.api.MailingAddressResource} exposes the {@link fr.flst.jee.mmarie.core.MailingAddress}.
 */
@Path("/mailingAddress")
@Api("/mailingAddress")
@Slf4j
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class MailingAddressResource {
    private final MailingAddressService mailingAddressService;
    private final UserService userService;

    @Inject
    public MailingAddressResource(MailingAddressService mailingAddressService, UserService userService) {
        this.mailingAddressService = mailingAddressService;
        this.userService = userService;
    }

    /**
     * <p>
     *     Find an {@link fr.flst.jee.mmarie.dto.MailingAddressDto} by the {@code mailingAddressId}.
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @param mailingAddressId Id of the {@link fr.flst.jee.mmarie.core.MailingAddress}
     * @return The mailingAddress.
     * @throws com.sun.jersey.api.NotFoundException if the author has not been found.
     */
    @GET
    @ApiOperation("Get a mailing address by id")
    @Timed
    @Path("/{mailingAddressId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public MailingAddressDto findById(@Auth User user, @PathParam("mailingAddressId") IntParam mailingAddressId) {
        return mailingAddressService.findById(mailingAddressId);
    }

    /**
     * <p>
     *     Update a {@link fr.flst.jee.mmarie.dto.MailingAddressDto}.
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @param mailingAddressDto {@link fr.flst.jee.mmarie.dto.MailingAddressDto} to update.
     * @return The updated mailingAddress.
     * @throws com.sun.jersey.api.NotFoundException if the author has not been found.
     */
    @PUT
    @ApiOperation("Update a mailing address")
    @Timed
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public MailingAddressDto update(@Auth User user, @Valid MailingAddressDto mailingAddressDto) {
        log.info("[{}] - Update mailing address [{}]", user, mailingAddressDto);

        MailingAddress persistedAddress = mailingAddressService.persist(mailingAddressDto);

        if(user.getMailingAddress() == null || user.getMailingAddress().getId() != mailingAddressDto.getId()) {
            userService.updateMailingAddress(user, persistedAddress);
        }

        return mailingAddressService.converts(persistedAddress);
    }
}
