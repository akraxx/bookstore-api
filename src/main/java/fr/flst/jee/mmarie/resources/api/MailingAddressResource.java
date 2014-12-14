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
 * Created by Maximilien on 19/10/2014.
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

    @GET
    @ApiOperation("Get a mailing address by id")
    @Timed
    @Path("/{mailingAddressId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public MailingAddressDto findById(@Auth User user, @PathParam("mailingAddressId") IntParam mailingAddressId) {
        return mailingAddressService.findById(mailingAddressId);
    }

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
