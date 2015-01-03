package fr.flst.jee.mmarie.resources.api;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.google.inject.Inject;
import fr.flst.jee.mmarie.core.User;
import fr.flst.jee.mmarie.dto.OrderLineDto;
import fr.flst.jee.mmarie.services.OrderLineService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.IntParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * {@link fr.flst.jee.mmarie.resources.api.OrderLineResource} exposes the {@link fr.flst.jee.mmarie.core.OrderLine}.
 */
@Path("/orderLine")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class OrderLineResource {
    private OrderLineService orderLineService;

    @Inject
    public OrderLineResource(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }

    /**
     * <p>
     *     Find a list of {@link fr.flst.jee.mmarie.dto.OrderLineDto} by the {@code bookIsbn13}.
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @param bookIsbn13 Isbn13 of the {@link fr.flst.jee.mmarie.core.Book}
     * @return List of order lines
     */
    @GET
    @Timed
    @Path("/byBook/{bookIsbn13}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public List<OrderLineDto> findByBookIsbn13(@Auth User user, @PathParam("bookIsbn13") String bookIsbn13) {
        return orderLineService.findByBookIsbn13(bookIsbn13);
    }

    /**
     * <p>
     *     Find a list of {@link fr.flst.jee.mmarie.dto.OrderLineDto} by the {@code orderId}.
     * </p>
     * <p>
     *     Resource protected with {@link io.dropwizard.auth.Auth}.
     * </p>
     *
     * @param user Logged {@link fr.flst.jee.mmarie.core.User}
     * @param orderId Id of the {@link fr.flst.jee.mmarie.core.Order}
     * @return List of order lines
     */
    @GET
    @Timed
    @Path("/byOrder/{orderId}")
    @UnitOfWork
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public List<OrderLineDto> findByOrderId(@Auth User user, @PathParam("orderId") IntParam orderId) {
        return orderLineService.findByOrderId(orderId);
    }
}
