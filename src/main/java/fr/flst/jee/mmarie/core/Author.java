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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *     {@link fr.flst.jee.mmarie.core.Author} is an {@link javax.persistence.Entity}.
 * </p>
 *
 * Table name : {@code AUTHORS}
 *
 * Columns :
 * <ul>
 *     <li>{@link javax.persistence.Id}, {@link javax.persistence.GeneratedValue} : {@code ID} not {@code nullable}</li>
 *     <li>{@code FIRST_NAME} not {@code nullable}</li>
 *     <li>{@code LAST_NAME} not {@code nullable}</li>
 *     <li>{@code BIRTH_DATE}</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "books")
@Builder
@Entity
@Table(name = "AUTHORS")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "BIRTH_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Book> books;
}
