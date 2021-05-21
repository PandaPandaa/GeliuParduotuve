package GP.persistence;

import GP.entities.Flower;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class FlowersDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Flower> loadAll() {
        return em.createNamedQuery("Flower.findAll", Flower.class).getResultList();
    }

    public void persist(Flower flower){
        this.em.persist(flower);
    }

    public Flower findOne(Integer id){ return em.find(Flower.class, id); }

    public Flower update(Flower flower){
        return em.merge(flower);
    }
}
