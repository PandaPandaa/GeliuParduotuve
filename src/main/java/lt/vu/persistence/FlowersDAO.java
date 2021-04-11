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
        List<Flower> a = em.createNamedQuery("Flower.findAll", Flower.class).getResultList();
        for (Flower f :
             a) {
            System.out.println(f.getName());
        }
        return a;
    }

    public void persist(Flower flower){
        System.out.println(flower.getName());
        System.out.println(flower.getPrice());
        this.em.persist(flower);
    }

    public Flower findOne(Integer id){ return em.find(Flower.class, id); }

    public Flower update(Flower flower){
        return em.merge(flower);
    }
}
