package fr.flst.jee.mmarie.services;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.NotFoundException;
import fr.flst.jee.mmarie.core.MailingAddress;
import fr.flst.jee.mmarie.db.dao.interfaces.MailingAddressDAO;
import fr.flst.jee.mmarie.dto.MailingAddressDto;
import fr.flst.jee.mmarie.misc.DtoMappingService;
import io.dropwizard.jersey.params.IntParam;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Singleton
public class MailingAddressService {
    private final MailingAddressDAO mailingAddressDAO;

    private final DtoMappingService dtoMappingService;

    public MailingAddress findSafely(Integer id) {
        final Optional<MailingAddress> mailingAddress = mailingAddressDAO.findById(id);
        if (!mailingAddress.isPresent()) {
            throw new NotFoundException("No such mailingAddress.");
        }
        return mailingAddress.get();
    }

    @Inject
    public MailingAddressService(MailingAddressDAO mailingAddressDAO, DtoMappingService dtoMappingService) {
        this.mailingAddressDAO = mailingAddressDAO;
        this.dtoMappingService = dtoMappingService;
    }

    public MailingAddressDto findById(Integer mailingAddressId) {
        return dtoMappingService.convertsToDto(findSafely(mailingAddressId), MailingAddressDto.class);
    }

    public MailingAddressDto findById(IntParam mailingAddressId) {
        return findById(mailingAddressId.get());
    }

    public MailingAddress persist(MailingAddressDto mailingAddressDto) {
        MailingAddress address = dtoMappingService.convertsToModel(mailingAddressDto, MailingAddress.class);

        return mailingAddressDAO.persist(address);
    }

    public MailingAddressDto converts(MailingAddress mailingAddress) {
        return dtoMappingService.convertsToDto(mailingAddress, MailingAddressDto.class);
    }
}
