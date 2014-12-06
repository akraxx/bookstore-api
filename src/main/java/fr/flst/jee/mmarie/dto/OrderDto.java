package fr.flst.jee.mmarie.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

import java.util.Date;

/**
 * Created by Maximilien on 21/10/2014.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orderDate"})
@ToString
@Builder
public class OrderDto {
    private int id;

    private Integer userId;

    private Date orderDate;

    private Integer mailingAddressId;
}
