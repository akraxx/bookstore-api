package fr.flst.jee.mmarie.core;

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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * <p>
 *     {@link fr.flst.jee.mmarie.core.Book} is an {@link javax.persistence.Entity}.
 * </p>
 *
 * Table name : {@code BOOKS}
 *
 * Columns :
 * <ul>
 *     <li>{@link javax.persistence.Id} : {@code ISBN13} not {@code nullable}</li>
 *     <li>{@code TITLE} not {@code nullable}</li>
 *     <li>{@code UNIT_PRICE} not {@code nullable}</li>
 *     <li>{@code EDITOR}</li>
 *     <li>{@code AUTHORS_ID}</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "orderLines")
@Builder
@Entity
@EqualsAndHashCode(of = "isbn13")
@Table(name = "BOOKS")
@NamedQueries(value = {
        @NamedQuery(
                name = Book.FIND_BY_AUTHOR_ID,
                query = "SELECT b FROM Book b WHERE b.author.id = :authorId"
        ),
        @NamedQuery(
                name = Book.FIND_ALL,
                query = "SELECT b FROM Book b"
        )
})
public class Book {
    public static final String FIND_BY_AUTHOR_ID = "fr.flst.jee.mmarie.core.Book.findByAuthorId";
    public static final String FIND_ALL = "fr.flst.jee.mmarie.core.Book.findAll";

    @Id
    @Column(name = "ISBN13", nullable = false)
    private String isbn13;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "UNIT_PRICE", nullable = false)
    private Double unitPrice;

    @Column(name = "EDITOR")
    private String editor;

    @ManyToOne
    @JoinColumn(name = "AUTHORS_ID")
    private Author author;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.book", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderLine> orderLines;
}
