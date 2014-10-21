package fr.flst.jee.mmarie.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Maximilien on 21/10/2014.
 */
@Embeddable
@Getter
@Setter
@ToString
public class OrderLineId implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    private Book book;
}
