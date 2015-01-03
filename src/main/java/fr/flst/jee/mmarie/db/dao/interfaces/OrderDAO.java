package fr.flst.jee.mmarie.db.dao.interfaces;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.Order;

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
}
