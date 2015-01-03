package fr.flst.jee.mmarie.core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 *     {@link fr.flst.jee.mmarie.core.MailingAddress} is an {@link javax.persistence.Entity}.
 * </p>
 *
 * Table name : {@code MAILING_ADDRESSES}
 *
 * Columns :
 * <ul>
 *     <li>{@link javax.persistence.Id}, {@link javax.persistence.GeneratedValue} : {@code ID} not {@code nullable}</li>
 *     <li>{@code ZIP} not {@code nullable}, {@code length} = 10</li>
 *     <li>{@code CITY} not {@code nullable}, {@code length} = 45</li>
 *     <li>{@code LINE1} not {@code nullable}, {@code length} = 80</li>
 *     <li>{@code LINE2}, {@code length} = 80</li>
 *     <li>{@code LINE3}, {@code length} = 80</li>
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
@Table(name = "MAILING_ADDRESSES")
public class MailingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "ZIP", nullable = false, length = 10)
    private String zip;

    @Column(name = "CITY", nullable = false, length = 45)
    private String city;

    @Column(name = "LINE1", nullable = false, length = 80)
    private String line1;

    @Column(name = "LINE2", nullable = true, length = 80)
    private String line2;

    @Column(name = "LINE3", nullable = true, length = 80)
    private String line3;
}
