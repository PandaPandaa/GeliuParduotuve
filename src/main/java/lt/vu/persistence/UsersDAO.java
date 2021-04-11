package lt.vu.persistence;

import lt.vu.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class UsersDAO {

    @Inject
    private EntityManager em;

    public void persist(User user){
        this.em.persist(user);
    }

    public User findOne(Integer id){ return em.find(User.class, id); }

    public User update(User user){
        return em.merge(user);
    }
}
