package fr.flst.jee.mmarie.dto;

import fr.flst.jee.mmarie.core.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

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
public class UserDto implements Dto<User> {
    private String login;

    private String email;

    private String password;

    private Integer mailingAddressId;
}
