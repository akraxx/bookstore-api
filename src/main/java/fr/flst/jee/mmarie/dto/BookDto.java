package fr.flst.jee.mmarie.dto;

import fr.flst.jee.mmarie.core.Book;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;
import org.dozer.Mapping;

/**
 * {@link fr.flst.jee.mmarie.dto.Dto} implementation of {@link fr.flst.jee.mmarie.core.Book}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class BookDto implements Dto<Book> {
    private String isbn13;

    private String title;

    private Double unitPrice;

    private String editor;

    @Mapping(value = "author.id")
    private Integer authorId;
}
