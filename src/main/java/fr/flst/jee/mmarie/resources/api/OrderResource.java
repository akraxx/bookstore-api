package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.dto.OrderDto;
import fr.flst.jee.mmarie.services.OrderService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * {@link fr.flst.jee.mmarie.resources.api.OrderResource} exposes the {@link fr.flst.jee.mmarie.core.Order}.
 */
@Path("/order")
@Api("/order")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class OrderResource {

    private OrderService orderService;

    @Inject
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * <p>
     *     Find an {@link fr.flst.jee.mmarie.dto.OrderDto} by the {@code orderId}.
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @param orderId Id of the {@link fr.flst.jee.mmarie.core.Order}
     * @return The order.
     * @throws com.sun.jersey.api.NotFoundException if the author has not been found.
     */
    @GET
    @ApiOperation("Get order by id")
    @Timed
    @Path("/{orderId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public OrderDto findById(@Auth User user, @PathParam("orderId") IntParam orderId) {
        return orderService.findById(orderId);
    }
}
