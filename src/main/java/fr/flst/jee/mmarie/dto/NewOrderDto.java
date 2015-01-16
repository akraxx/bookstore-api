package fr.flst.jee.mmarie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import java.util.List;

/**
 * Created by Maximilien on 09/01/2015.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderDto {

    private List<OrderLineDto> orderLines;

    private MailingAddressDto address;

}
