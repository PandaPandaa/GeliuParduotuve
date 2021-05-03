package lt.vu.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import lt.vu.enums.OrderStatus;
import lt.vu.utilities.OrderInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Column(name = "STATUS")
    private OrderStatus status;

    @Column(name = "INFO")
    private String orderInfo;

    public Order() {
    }

    public List<OrderInfo> getOrderInfo() {
        Gson gson = new Gson();
        return gson.fromJson(orderInfo, new TypeToken<List<OrderInfo>>(){}.getType());
    }

    public void setOrderInfo(List<OrderInfo> orderInfos) {
        Gson gson = new Gson();
        this.orderInfo = gson.toJson(orderInfos);
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
