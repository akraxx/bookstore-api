package fr.flst.jee.mmarie.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.beans.Transient;

/**
 * <p>
 *     {@link fr.flst.jee.mmarie.core.OrderLine} is an {@link javax.persistence.Entity}.
 * </p>
 *
 * Table name : {@code ORDER_LINES}
 *
 * Columns :
 * <ul>
 *     <li>{@code QUANTITY}</li>
 *     <li>{@code BOOKS_ID}</li>
 *     <li>{@code ORDERS_ID}</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "ORDER_LINES")
@AssociationOverrides({
        @AssociationOverride(name = "pk.book",
                joinColumns = @JoinColumn(name = "BOOKS_ID")),
        @AssociationOverride(name = "pk.order",
                joinColumns = @JoinColumn(name = "ORDERS_ID"))})
@NamedQueries(value = {
        @NamedQuery(
                name = OrderLine.FIND_BY_BOOK_ISBN13,
                query = "SELECT ol FROM OrderLine ol WHERE ol.pk.book.isbn13 = :bookIsbn13"
        ),
        @NamedQuery(
                name = OrderLine.FIND_BY_ORDER_ID,
                query = "SELECT ol FROM OrderLine ol WHERE ol.pk.order.id = :orderId"
        )
})
public class OrderLine {
    public static final String FIND_BY_BOOK_ISBN13 = "fr.flst.jee.mmarie.core.OrderLine.findByBookIsbn13";
    public static final String FIND_BY_ORDER_ID = "fr.flst.jee.mmarie.core.OrderLine.findByOrderId";

    @EmbeddedId
    @JsonIgnore
    private OrderLineId pk;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Transient
    public Book getBook() {
        if (pk != null) {
            return pk.getBook();
        } else {
            return null;
        }
    }

    public void setBook(Book book) {
        if (pk != null) {
            pk.setBook(book);
        }
    }

    @Transient
    public Order getOrder() {
        if (pk != null) {
            return pk.getOrder();
        } else {
            return null;
        }
    }

    public void setOrder(Order order) {
        if (pk != null) {
            pk.setOrder(order);
        }
    }
}
