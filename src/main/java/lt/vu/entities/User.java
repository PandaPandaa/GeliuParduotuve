package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;
import lt.vu.enums.UserType;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "select u from User as u")
})
@Table(name = "USERS")
@Getter @Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "HASHED_PASSWORD")
    private String password;

    @Column(name = "USERTYPE")
    private UserType type;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

    public User() {
    }

    public User(String email, String name, String password, UserType type) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
