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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * {@link fr.flst.jee.mmarie.dto.Dto} implementation of {@link fr.flst.jee.mmarie.core.OrderLine}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class OrderLineDto implements Dto<OrderLine> {
    @Min(1)
    private int quantity;

    @NotNull
    @Size(min = 14, max = 14)
    @Mapping("pk.book.isbn13")
    private String bookIsbn13;

    @Mapping("pk.order.id")
    private Integer orderId;
}
