package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import fr.flst.jee.mmarie.core.OrderLine;
import fr.flst.jee.mmarie.services.OrderLineService;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Path("/api/orderLine")
@Api("/api/orderLine")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class OrderLineResource {
    private OrderLineService orderLineService;

    @Inject
    public OrderLineResource(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }

    @GET
    @ApiOperation("Get order lines by book isbn13")
    @Timed
    @Path("/byBook/{bookIsbn13}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public List<OrderLine> findByBookIsbn13(@PathParam("bookIsbn13") String bookIsbn13) {
        return orderLineService.findByBookIsbn13(bookIsbn13);
    }

    @GET
    @ApiOperation("Get order lines by order id")
    @Timed
    @Path("/byOrder/{orderId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public List<OrderLine> findByOrderId(@PathParam("orderId") IntParam orderId) {
        return orderLineService.findByOrderId(orderId);
    }
}
