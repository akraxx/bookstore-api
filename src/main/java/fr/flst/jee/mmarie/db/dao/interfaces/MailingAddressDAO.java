package fr.flst.jee.mmarie.db.dao.interfaces;

import com.google.common.base.Optional;
import fr.flst.jee.mmarie.core.MailingAddress;

/**
 * {@link fr.flst.jee.mmarie.db.dao.interfaces.MailingAddressDAO} defines which queries are available
 * to manipulate an {@link fr.flst.jee.mmarie.core.MailingAddress}
 */
public interface MailingAddressDAO {
    /**
     * Find an {@link fr.flst.jee.mmarie.core.MailingAddress} by it's {@code id}.
     *
     * @param id Id of the mailingAddress.
     * @return The mailingAddress if it exists, {@link com.google.common.base.Optional#absent()} otherwise.
     */
    Optional<MailingAddress> findById(Integer id);

    /**
     * Persist the {@code mailingAddress} into the database.
     *
     * @param mailingAddress The {@link fr.flst.jee.mmarie.core.MailingAddress} to persist.
     * @return The persisted instance.
     */
    MailingAddress persist(MailingAddress mailingAddress);
}
