package GP.usecases;

import GP.entities.Order;
import GP.entities.User;
import GP.enums.OrderStatus;
import GP.interfaces.PaymentController;
import GP.persistence.OrdersDAO;
import GP.utilities.OrderInfo;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Named
@RequestScoped
public class OrderController implements Serializable {

    @Inject
    private CurrentUser currentUser;

    @Inject
    private Cart cart;

    @Inject
    private OrdersDAO ordersDAO;

    @Inject
    private FlowerProcessing flowerProcessing;

    @Inject
    private PaymentController paymentController;

    @Getter @Setter
    private Order orderToCreate = new Order();

    @Transactional
    public String PlaceOrder(User user, List<OrderInfo> list)
    {
        if(paymentController.pay())
        {
            CompletableFuture.runAsync(() -> ProcessOrder(user, list));
            flowerProcessing.ReduceFlowerRemainder(list);
        }
        return "orderPlacement?faces-redirect=true";
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void ProcessOrder(User user, List<OrderInfo> list) {
        orderToCreate.setDate(LocalDateTime.now());
        orderToCreate.setUser(user.getId() != null ? user : null);
        orderToCreate.setStatus(OrderStatus.ACCEPTED);
        orderToCreate.setOrderInfo(list);
        this.ordersDAO.persist(orderToCreate);
    }

    @Transactional
    public void CancelOrder(Integer orderId)
    {
        Order order = ordersDAO.findOne(orderId);
        order.setStatus(OrderStatus.CANCELED);
        ordersDAO.update(order);
        flowerProcessing.IncreaseFlowerRemainder(order.getOrderInfo());
    }

    @Transactional
    public void upgradeOrderStatus(String id)
    {
        if(!id.equals(""))
        {
            int i = Integer.parseInt(id);
            Order o = ordersDAO.findOne(i);
            o.setStatus(OrderStatus.values()[(o.getStatus().ordinal() + 1) % OrderStatus.values().length]);
        }
    }
}
