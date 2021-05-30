package GP.usecases;

import GP.entities.Flower;
import GP.entities.Order;
import GP.enums.FlowerCategory;
import GP.enums.OrderStatus;
import GP.interfaces.PaymentController;
import GP.persistence.FlowersDAO;
import GP.persistence.OrdersDAO;
import lombok.Getter;
import lombok.Setter;
import GP.interceptors.LoggedInvocation;
import org.primefaces.model.file.UploadedFile;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Model
public class Orders {

    @Inject
    private OrdersDAO ordersDAO;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private PaymentController paymentController;

    public List<Order> loadAllOrders()
    {
        return ordersDAO.loadAll();
    }

    public List<Order> loadCurrentUserOrders()
    {
        return ordersDAO.loadByCurrentUser(currentUser.getCurrentUser());
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

    public double getOrderPrice()
    {
        return paymentController.getPrice();
    }
}
