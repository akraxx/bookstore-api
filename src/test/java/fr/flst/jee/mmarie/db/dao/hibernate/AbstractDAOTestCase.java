package fr.flst.jee.mmarie.db.dao.hibernate;

import fr.flst.jee.mmarie.TestUtils;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Maximilien on 01/10/2014.
 */
public class AbstractDAOTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDAOTestCase.class);

    protected static EntityManagerFactory emf;
    protected static EntityManager em;
    protected static SessionFactory sessionFactory;

    private Transaction currentTransaction;
    @BeforeClass
    public static void initClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("BookstorePU");

        sessionFactory = emf.unwrap(SessionFactory.class);
    }

    @AfterClass
    public static void closeClass() {
        emf.close();
        TestUtils.closeJDBCConnection();
    }

    @Before
    public void initTest() throws Exception {
        //ask dbUnit to store them in db
        TestUtils.reloadDbUnitData();
        em = emf.createEntityManager();
        LOGGER.debug("BEFORE TEST");
        em.getTransaction().begin();
        currentTransaction = sessionFactory.getCurrentSession().beginTransaction();
    }

    @After
    public void closeTest() {
        LOGGER.debug("AFTER TEST");
        em.getTransaction().commit();
        currentTransaction.commit();
        em.clear();
        em.close();
    }
}
