package lt.vu.persistence;

import lt.vu.entities.LogException;
import lt.vu.entities.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class LoggerDAO {

    @PersistenceContext
    private EntityManager em;

    public void persist(LogException exc){
        this.em.persist(exc);
    }

}