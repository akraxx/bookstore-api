package fr.flst.jee.mmarie.db.dao.interfaces;

import fr.flst.jee.mmarie.core.OrderLine;

import java.util.List;

/**
 * Created by Maximilien on 21/10/2014.
 */
public interface OrderLineDAO {
    List<OrderLine> findByBookIsbn13(String isbn13);

    List<OrderLine> findByOrderId(Integer id);
}
