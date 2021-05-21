package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.User;
import lt.vu.enums.UserType;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.UsersDAO;

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
            if(currentUser.isLoggedIn())
            {
                currentUser.getCurrentUser().setName(user.getName());
                currentUser.getCurrentUser().setEmail(user.getEmail());
                currentUser.getCurrentUser().setPassword(user.getPassword());
                usersDAO.update(currentUser.getCurrentUser());
            }
        } catch (OptimisticLockException e) {
            return currentUser.getCurrentUser().getType().toString().toLowerCase(Locale.ROOT) + "?faces-redirect=true" + "&error=optimistic-lock-exception";
        }
        return currentUser.getCurrentUser().getType().toString().toLowerCase(Locale.ROOT) + "?faces-redirect=true";
    }
}
