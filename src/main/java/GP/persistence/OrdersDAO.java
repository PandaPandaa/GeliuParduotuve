package GP.persistence;

import GP.entities.Flower;
import GP.entities.Order;
import GP.enums.FlowerCategory;
import GP.usecases.CurrentUser;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class OrdersDAO {

    @PersistenceContext
    private EntityManager em;

    public void persist(Order order){
        this.em.persist(order);
    }

    public Order findOne(Integer id){ return em.find(Order.class, id);
    }

    public Order update(Order order){
        return em.merge(order);
    }

    public List<Order> loadAll() {
        return em.createNamedQuery("Order.findAll", Order.class).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Order> loadByCurrentUser() {
        return em.createQuery("Select o From Order o " +
                "Where o.user = :user ").setParameter("user", CurrentUser.currentUser).getResultList();
    }
}
