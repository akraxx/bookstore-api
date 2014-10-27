package fr.flst.jee.mmarie.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Maximilien on 21/10/2014.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "orders")
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @Column(name = "LOGIN", nullable = false, length = 30)
    private String login;

    @Column(name = "EMAIL", nullable = false, length = 80)
    private String email;

    @Column(name = "PWD", nullable = false, length = 20)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orders;

    @OneToOne
    @JoinColumn(name = "PERSONNAL_ADR_ID")
    private MailingAddress mailingAddress;
}
