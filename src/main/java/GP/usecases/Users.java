package GP.usecases;

import GP.entities.User;
import GP.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import GP.interceptors.LoggedInvocation;
import GP.persistence.UsersDAO;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.Locale;

@Model
public class Users {

    @Inject
    private UsersDAO usersDAO;

    @Inject
    private CurrentUser currentUser;

    @Getter @Setter
    private User user = new User();

    @LoggedInvocation
    @Transactional
    public String Register()
    {
        if(!this.usersDAO.userExists(user.getEmail()))
        {
            user.setType(UserType.CUSTOMER);
            this.usersDAO.persist(user);
        }
        currentUser.setCurrentUser(user);
        return "customer?faces-redirect=true";
    }

    @LoggedInvocation
    @Transactional
    public String Login(){
        User user = this.usersDAO.findByEmail(this.user.getEmail());
        if(user == null) return "null user";
        else if (!user.getPassword().equals(this.user.getPassword())) return "bad pass";
        else
        {
            currentUser.setCurrentUser(user);
            return user.getType().toString().toLowerCase(Locale.ROOT) + "?faces-redirect=true";
        }
    }

    @LoggedInvocation
    public String LogOut()
    {
        currentUser.setCurrentUser(new User());
        return "customer?faces-redirect=true";
    }

    @LoggedInvocation
    @Transactional
    public String UpdateCurrentUser()
    {
        try {
            currentUser.setCurrentUser(usersDAO.update(currentUser.getCurrentUser()));
        } catch (OptimisticLockException e) {
            return "userSettings?faces-redirect=true" + "&error=optimistic-lock-exception";
        }
        return "userSettings?faces-redirect=true";
    }

    @LoggedInvocation
    @Transactional
    public String OverwriteCurrentUser()
    {
        User u = usersDAO.findOne(currentUser.getCurrentUser().getId());
        currentUser.getCurrentUser().setVersion(u.getVersion());
        currentUser.setCurrentUser(usersDAO.update(currentUser.getCurrentUser()));
        System.out.println(currentUser.getCurrentUser().getName());

        return "userSettings?faces-redirect=true";
    }

    @LoggedInvocation
    @Transactional
    public String ReloadCurrentUser()
    {
        currentUser.setCurrentUser(usersDAO.findOne(currentUser.getCurrentUser().getId()));

        return "userSettings?faces-redirect=true";
    }
}
