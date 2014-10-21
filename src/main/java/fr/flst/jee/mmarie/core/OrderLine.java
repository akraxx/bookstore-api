package fr.flst.jee.mmarie.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
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
 * Created by Maximilien on 21/10/2014.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "ORDER_LINES")
@AssociationOverrides({
        @AssociationOverride(name = "pk.book",
                joinColumns = @JoinColumn(name = "BOOKS_ID")),
        @AssociationOverride(name = "pk.order",
                joinColumns = @JoinColumn(name = "ORDERS_ID")) })
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
        return pk.getBook();
    }

    public void setBook(Book book) {
        pk.setBook(book);
    }

    @Transient
    public Order getOrder() {
        return pk.getOrder();
    }

    public void setOrder(Order order) {
        pk.setOrder(order);
    }
}
