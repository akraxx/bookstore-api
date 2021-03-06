package fr.flst.jee.mmarie.db.dao.interfaces;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.Order;

import java.util.List;

/**
 * {@link fr.flst.jee.mmarie.db.dao.interfaces.OrderDAO} defines which queries are available
 * to manipulate an {@link fr.flst.jee.mmarie.core.Order}
 */
public interface OrderDAO {
    /**
     * Find an {@link fr.flst.jee.mmarie.core.Order} by it's {@code id}.
     *
     * @param id Id of the order.
     * @return The order if it exists, {@link com.google.common.base.Optional#absent()} otherwise.
     */
    Optional<Order> findById(Integer id);

    /**
     * Find an {@link fr.flst.jee.mmarie.core.Order} list of {@code login}.
     *
     * @param login Login of the user.
     * @return The list of orders.
     */
    List<Order> findByUserLogin(String login);

    /**
     * Persist the {code order} into the database.
     *
     * @param order The {@link fr.flst.jee.mmarie.core.Order} to persist.
     * @return The persisted instance.
     */
    Order persist(Order order);
}
