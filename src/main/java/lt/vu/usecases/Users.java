package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.User;
import lt.vu.enums.UserType;
import lt.vu.persistence.UsersDAO;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Locale;

@Model
public class Users {

    @Inject
    private UsersDAO usersDAO;

    @Getter @Setter
    private User currentUser = new User();

    @Transactional
    public String Register()
    {
        if(!this.usersDAO.userExists(currentUser.getEmail()))
        {
            this.usersDAO.persist(new User(currentUser.getEmail(), currentUser.getName(), currentUser.getPassword(), UserType.CUSTOMER));
        }
        return "customer?faces-redirect=true";
    }

    @Transactional
    public String Login(){
        User user = this.usersDAO.findByEmail(currentUser.getEmail());
        if(user == null) return "null user";
        else if (!user.getPassword().equals(currentUser.getPassword())) return "bad pass";
        else return user.getType().toString().toLowerCase(Locale.ROOT) + "?faces-redirect=true";
    }

    public void LogOut()
    {
        currentUser = new User();
    }
}
