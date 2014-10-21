package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.services.MailingAddressService;
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
@Path("/api/mailingAddress")
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
public class MailingAddressResource {
    private MailingAddressService mailingAddressService;

    @Inject
    public MailingAddressResource(MailingAddressService mailingAddressService) {
        this.mailingAddressService = mailingAddressService;
    }

    @GET
    @Timed
    @Path("{mailingAddressId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
    public MailingAddress findById(@PathParam("mailingAddressId") IntParam mailingAddressId) {
        return mailingAddressService.findById(mailingAddressId);
    }
}
