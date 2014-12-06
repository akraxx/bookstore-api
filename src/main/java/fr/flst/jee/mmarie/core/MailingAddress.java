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
 * Created by Maximilien on 21/10/2014.
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
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
