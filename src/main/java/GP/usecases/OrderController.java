package GP.usecases;

import GP.interfaces.PaymentController;
import GP.entities.Order;
import GP.enums.OrderStatus;
import GP.interceptors.LoggedInvocation;
import GP.persistence.OrdersDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@LoggedInvocation
@Named
@RequestScoped
public class OrderController
{
    @Inject
    private Cart cart;

    @Inject
    private OrdersDAO ordersDAO;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private FlowerProcessing flowerProcessing;

    @Inject
    private PaymentController paymentController;

    @Transactional
    public void PlaceOrder(String address, String message)
    {
        if(paymentController.pay())
        {
            CompletableFuture.runAsync(() -> processOrder(address, message));
        }
    }

    @Transactional
    public void processOrder(String address, String message)
    {
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setUser(currentUser.isLoggedIn() ? currentUser.getCurrentUser() : null);
        order.setStatus(OrderStatus.ACCEPTED);
        order.setOrderInfo(cart.getOrderInfos());
        order.setAddress(address);
        order.setMessage(message);
        flowerProcessing.ReduceFlowerRemainder(cart.getOrderInfos());
        ordersDAO.persist(order);
    }

    @Transactional
    public void CancelOrder(Integer orderId)
    {
        Order order = ordersDAO.findOne(orderId);
        order.setStatus(OrderStatus.CANCELED);
        ordersDAO.update(order);
        flowerProcessing.IncreaseFlowerRemainder(order.getOrderInfo());
    }

    public List<Order> loadAllOrders()
    {
        return ordersDAO.loadAll();
    }
}
