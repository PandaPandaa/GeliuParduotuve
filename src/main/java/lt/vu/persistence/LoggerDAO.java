package lt.vu.persistence;

import lt.vu.entities.LogException;
import lt.vu.entities.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class LoggerDAO {

    @Inject
    private EntityManager em;

    public void persist(LogException exc){
        this.em.persist(exc);
    }

}