package fr.flst.jee.mmarie.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;
import org.hibernate.validator.constraints.Email;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
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
@NamedQueries(value = {
        @NamedQuery(
                name = User.FIND_BY_USERNAME_AND_PASSWORD,
                query = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password"
        )
})
public class User {
    public static final String FIND_BY_USERNAME_AND_PASSWORD = "fr.flst.jee.mmarie.core.User.findByUsernameAndPassword";

    @Id
    @Column(name = "LOGIN", unique = true, nullable = false, length = 30)
    @JsonProperty(required = true)
    @Size(min = 5, max = 30, message = "The size of the username must be between {min} and {max}")
    private String login;

    @Column(name = "EMAIL", unique = true, nullable = false, length = 80)
    @JsonProperty(required = true)
    @Email(message = "Malformed email")
    private String email;

    @Column(name = "PWD", nullable = false, length = 20)
    @JsonProperty(required = true)
    @Size(min = 6, max = 20, message = "The size of the password must be between {min} and {max}")
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PERSONNAL_ADR_ID")
    private MailingAddress mailingAddress;
}
