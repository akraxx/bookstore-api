package fr.flst.jee.mmarie.dto;

import fr.flst.jee.mmarie.core.MailingAddress;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * {@link fr.flst.jee.mmarie.dto.Dto} implementation of {@link fr.flst.jee.mmarie.core.MailingAddress}
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

    @NotEmpty(message = "The zip code can not be empty")
    @Size(min = 4, max = 10, message = "The size of the zip code must be between {min} and {max}")
    private String zip;

    @NotEmpty(message = "The city name can not be empty")
    @Size(max = 45, message = "The length of the city name is too big, max allowed is {max}")
    private String city;

    @NotEmpty(message = "The first line can not be empty")
    @Size(max = 80, message = "The length of the first address line is too big, max allowed is {max}")
    private String line1;

    private String line2;

    private String line3;
}
