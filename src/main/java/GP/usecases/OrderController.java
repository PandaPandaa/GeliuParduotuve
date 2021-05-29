package GP.usecases;

import GP.entities.Flower;
import GP.enums.FlowerCategory;
import GP.interfaces.PaymentController;
import GP.entities.Order;
import GP.enums.OrderStatus;
import GP.interceptors.LoggedInvocation;
import GP.persistence.OrdersDAO;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    private DataModel<Order> model;

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

    public Map<String, OrderStatus> getOrderStatusMap()
    {
        Map<String, OrderStatus> map = new LinkedHashMap<String, OrderStatus>();
        for (OrderStatus o:
                OrderStatus.values()) {
            map.put(o.name(), o);
        }
        return  map;
    }

    @Transactional
    public void upogradeOrderStatus(String id)
    {
        if(!id.equals(""))
        {
            int i = Integer.parseInt(id);
            Order o = ordersDAO.findOne(i);
            o.setStatus(OrderStatus.values()[(o.getStatus().ordinal() + 1) % OrderStatus.values().length]);
        }
    }
}
