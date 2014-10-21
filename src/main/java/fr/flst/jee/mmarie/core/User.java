package fr.flst.jee.mmarie.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
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
}
