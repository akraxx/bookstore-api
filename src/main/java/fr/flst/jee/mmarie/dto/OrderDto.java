package fr.flst.jee.mmarie.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr.flst.jee.mmarie.core.Order;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;
import org.dozer.Mapping;

import java.util.Date;

/**
 * {@link fr.flst.jee.mmarie.dto.Dto} implementation of {@link fr.flst.jee.mmarie.core.Order}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orderDate"})
@ToString
@Builder
public class OrderDto implements Dto<Order> {
    private int id;

    @Mapping("user.login")
    private String userLogin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd, HH:mm:ss", timezone = "CET")
    private Date orderDate;

    @Mapping("mailingAddress.id")
    private Integer mailingAddressId;
}
