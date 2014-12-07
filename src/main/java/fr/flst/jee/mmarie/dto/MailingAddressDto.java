package fr.flst.jee.mmarie.dto;

import fr.flst.jee.mmarie.core.MailingAddress;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

/**
 * Created by Maximilien on 21/10/2014.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class MailingAddressDto implements Dto<MailingAddress> {
    private int id;

    private String zip;

    private String city;

    private String line1;

    private String line2;

    private String line3;
}
