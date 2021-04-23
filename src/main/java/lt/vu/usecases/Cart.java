package lt.vu.usecases;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Flower;
import lt.vu.entities.Order;
import lt.vu.entities.User;
import lt.vu.enums.OrderStatus;
import lt.vu.persistence.OrdersDAO;
import lt.vu.utilities.OrderInfo;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class Cart implements Serializable {

    @Inject
    private CurrentUser currentUser;

    @Inject
    private OrdersDAO ordersDAO;

    @Getter @Setter
    private List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

    public String addToCart(Flower flower, Integer amount)
    {
        if(orderInfos.stream().anyMatch(o -> o.getFlowerName().equals(flower.getName())))
        {
            OrderInfo orderInfo = orderInfos.stream().filter(o -> o.getFlowerName().equals(flower.getName())).findFirst().get();
            if(orderInfo.getFlowerAmount() + amount > flower.getRemainder())
            {
                throw new UnsupportedOperationException(); //Priklausomai nuo GUI reiks padaryt kad neleistu useriui pridet daugiau geliu nei galima
            }
            else orderInfo.IncreaseAmount(amount);
        }
        else orderInfos.add(new OrderInfo(flower.getName(), flower.getPrice(), amount));


        //MANUAL TEST (seems to work)
        System.out.println();
        System.out.println("CART:");
        System.out.println();
        System.out.println();
        for (OrderInfo o:
             orderInfos) {
            System.out.println(o.getFlowerName() + "  " + o.getFlowerAmount());
        }



        return "flowerDetails?faces-redirect=true&flowerId=" + flower.getId();
    }

    @Transactional
    public void PlaceOrder()
    {
        System.out.println("a");
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setUser(currentUser.isLoggedIn() ? currentUser.getCurrentUser() : new User());
        order.setStatus(OrderStatus.ACCEPTED);
        Gson gson = new Gson();
        order.setSerializedOrderInfo(gson.toJson(orderInfos));
        ordersDAO.persist(order);
    }

}
