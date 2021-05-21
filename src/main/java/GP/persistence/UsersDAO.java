package GP.persistence;

import GP.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class UsersDAO {

    @PersistenceContext
    private EntityManager em;

    public void persist(User user){
        this.em.persist(user);
    }

    public User findByEmail(String email)
    {
        return (User)em.createQuery("Select u From User u " +
                                "Where u.email = :uEmail ").setParameter("uEmail", email).getSingleResult();
    }

    public boolean userExists(String email)
    {
        return 0 != (Long)em.createQuery("Select count(u) From User u " +
                "Where u.email = :uEmail ").setParameter("uEmail", email).getSingleResult();
    }

    public User update(User user){
        return em.merge(user);
    }
}
