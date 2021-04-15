package lt.vu.persistence;

import lombok.var;
import lt.vu.entities.Flower;
import lt.vu.entities.Team;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class FlowersDAO {

    @Inject
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
