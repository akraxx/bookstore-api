package fr.flst.jee.mmarie.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
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
 * Created by Maximilien on 19/10/2014.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "orderLines")
@Builder
@Entity
@Table(name = "BOOKS")
@NamedQueries(value = {
        @NamedQuery(
                name = Book.FIND_BY_AUTHOR_ID,
                query = "SELECT b FROM Book b WHERE b.author.id = :authorId"
        )
})
public class Book {
    public static final String FIND_BY_AUTHOR_ID = "fr.flst.jee.mmarie.core.findByAuthorId";

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
