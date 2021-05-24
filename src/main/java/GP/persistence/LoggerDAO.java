package GP.persistence;

import GP.entities.LogException;

import javax.enterprise.context.ApplicationScoped;
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