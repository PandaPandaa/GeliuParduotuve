package GP.entities;

import GP.enums.OrderStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import GP.utilities.OrderInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "RECEIVER_NAME")
    private String receiverName;

    @Column(name = "ADDRESS")
    private String address;

    public Order() {
    }

    public String getDate()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(formatter);
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
