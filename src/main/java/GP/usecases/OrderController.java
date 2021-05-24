package GP.usecases;

import GP.interfaces.FlowerProcessing;
import GP.entities.Order;
import GP.enums.OrderStatus;
import GP.interceptors.LoggedInvocation;
import GP.persistence.OrdersDAO;
import GP.utilities.OrderInfo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@LoggedInvocation
@Named
@RequestScoped
public class OrderController
{
    @Inject
    private OrdersDAO ordersDAO;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private FlowerProcessing flowerProcessing;

    @Transactional
    public void PlaceOrder(List<OrderInfo> orderInfos)
    {
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setUser(currentUser.isLoggedIn() ? currentUser.getCurrentUser() : null);
        order.setStatus(OrderStatus.ACCEPTED);
        order.setOrderInfo(orderInfos);
        flowerProcessing.ReduceFlowerRemainder(orderInfos);
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
