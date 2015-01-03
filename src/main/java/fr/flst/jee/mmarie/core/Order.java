package fr.flst.jee.mmarie.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/**
 * <p>
 *     {@link fr.flst.jee.mmarie.core.Order} is an {@link javax.persistence.Entity}.
 * </p>
 *
 * Table name : {@code ORDERS}
 *
 * Columns :
 * <ul>
 *     <li>{@link javax.persistence.Id}, {@link javax.persistence.GeneratedValue} : {@code ID} not {@code nullable}</li>
 *     <li>{@code USERS_LOGIN}</li>
 *     <li>{@code ORDER_DATE} not {@code nullable}</li>
 *     <li>{@code SHIPPING_ADR_ID}</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orderDate"})
@ToString(exclude = "orderLines")
@Builder
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "USERS_LOGIN")
    private User user;

    @Column(name = "ORDER_DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd, HH:mm:ss", timezone = "CET")
    private Date orderDate;

    @OneToOne
    @JoinColumn(name = "SHIPPING_ADR_ID")
    private MailingAddress mailingAddress;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.order", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderLine> orderLines;
}
