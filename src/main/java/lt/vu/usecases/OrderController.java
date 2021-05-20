package lt.vu.usecases;

import lt.vu.entities.Order;
import lt.vu.enums.OrderStatus;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.FlowersDAO;
import lt.vu.persistence.OrdersDAO;
import lt.vu.utilities.OrderInfo;

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
        ordersDAO.persist(order);
        flowerProcessing.ReduceFlowerRemainder(orderInfos);
    }

    @Transactional
    public void CancelOrder(Integer orderId)
    {
        Order order = ordersDAO.findOne(orderId);
        order.setStatus(OrderStatus.CANCELED);
        flowerProcessing.IncreaseFlowerRemainder(order.getOrderInfo());
    }
}
