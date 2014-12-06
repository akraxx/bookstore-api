package fr.flst.jee.mmarie.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr.flst.jee.mmarie.core.Author;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

import java.util.Date;

/**
 * Created by Maximilien on 19/10/2014.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class AuthorDto implements Dto<Author> {
    private int id;

    private String firstName;

    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
}
