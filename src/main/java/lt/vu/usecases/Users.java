package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.User;
import lt.vu.enums.UserType;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.UsersDAO;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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
    public void LogOut()
    {
        currentUser.setCurrentUser(new User());
//        return "customer?faces-redirect=true";
    }
}
