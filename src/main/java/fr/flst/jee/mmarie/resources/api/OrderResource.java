package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import fr.flst.jee.mmarie.core.Order;
import fr.flst.jee.mmarie.services.OrderService;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Path("/api/order")
@Produces(MediaType.APPLICATION_JSON+"; charset=UTF-8")
public class OrderResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

    private OrderService orderService;

    @Inject
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GET
    @Timed
    @Path("{orderId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
    public Order findById(@PathParam("orderId") IntParam orderId) {

        Order order = orderService.findById(orderId);
        LOGGER.info("Order lines [{}]", order.getOrderLines());
        return order;
    }
}
