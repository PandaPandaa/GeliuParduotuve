package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class CurrentUser implements Serializable {

    @Getter @Setter
    private User currentUser = new User();

    public boolean isLoggedIn()
    {
        return currentUser.getId() != null;
    }
}
