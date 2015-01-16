package fr.flst.jee.mmarie.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.flst.jee.mmarie.core.OrderLine;
import fr.flst.jee.mmarie.db.dao.interfaces.OrderLineDAO;
import fr.flst.jee.mmarie.dto.OrderLineDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import io.dropwizard.jersey.params.IntParam;

import java.util.List;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class OrderLineService {
    private final OrderLineDAO orderLineDAO;

    private final DtoMappingService dtoMappingService;

    @Inject
    public OrderLineService(OrderLineDAO orderLineDAO, DtoMappingService dtoMappingService) {
        this.orderLineDAO = orderLineDAO;
        this.dtoMappingService = dtoMappingService;
    }

    public List<OrderLineDto> findByBookIsbn13(String bookIsbn13) {
        return dtoMappingService.convertsListToDto(orderLineDAO.findByBookIsbn13(bookIsbn13), OrderLineDto.class);
    }

    public List<OrderLineDto> findByOrderId(IntParam orderId) {
        return dtoMappingService.convertsListToDto(orderLineDAO.findByOrderId(orderId.get()), OrderLineDto.class);
    }

    public OrderLineDto persist(OrderLine orderLine) {
        return dtoMappingService.convertsToDto(orderLineDAO.persist(orderLine), OrderLineDto.class);
    }
}
