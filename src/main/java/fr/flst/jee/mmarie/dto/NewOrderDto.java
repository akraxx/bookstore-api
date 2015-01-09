package fr.flst.jee.mmarie.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Maximilien on 09/01/2015.
 */
@Data
public class NewOrderDto {

    private List<OrderLineDto> orderLines;

    private MailingAddressDto address;

}
