package fr.flst.jee.mmarie.db.dao.interfaces;

import fr.flst.jee.mmarie.core.OrderLine;

import java.util.List;

/**
 * {@link fr.flst.jee.mmarie.db.dao.interfaces.OrderLineDAO} defines which queries are available
 * to manipulate an {@link fr.flst.jee.mmarie.core.OrderLine}
 */
public interface OrderLineDAO {
    /**
     * Find all {@link fr.flst.jee.mmarie.core.OrderLine} by {@code isbn13}.
     *
     * @param isbn13 Id of the book.
     * @return List of {@link fr.flst.jee.mmarie.core.OrderLine} which have a book with id {@code isbn13}.
     */
    List<OrderLine> findByBookIsbn13(String isbn13);

    /**
     * Find all {@link fr.flst.jee.mmarie.core.OrderLine} by {@code orderId}.
     *
     * @param orderId Id of the book.
     * @return List of {@link fr.flst.jee.mmarie.core.OrderLine} which have an order with id {@code orderId}.
     */
    List<OrderLine> findByOrderId(Integer orderId);

    /**
     * Persist the {code orderLine} into the database.
     *
     * @param orderLine The {@link fr.flst.jee.mmarie.core.OrderLine} to persist.
     * @return The persisted instance.
     */
    OrderLine persist(OrderLine orderLine);
}
