package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;
import lt.vu.enums.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "select o from Order as o")
})
@Table(name = "ORDERS")
@Getter @Setter
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Column(name = "STATUS")
    private OrderStatus status;

    @Column(name = "INFO")
    private String serializedOrderInfo;

    public Order() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
