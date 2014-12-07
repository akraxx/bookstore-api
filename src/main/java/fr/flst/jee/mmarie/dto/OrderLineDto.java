package fr.flst.jee.mmarie.dto;

import fr.flst.jee.mmarie.core.OrderLine;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;
import org.dozer.Mapping;

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
public class OrderLineDto implements Dto<OrderLine> {
    private int quantity;

    @Mapping("pk.book.isbn13")
    private String bookIsbn13;

    @Mapping("pk.order.id")
    private Integer orderId;
}
