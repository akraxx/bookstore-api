package fr.flst.jee.mmarie.core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * {@link fr.flst.jee.mmarie.core.OrderLineId} is {@link javax.persistence.Embeddable}.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderLineId implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOKS_ID")
    private Book book;
}
