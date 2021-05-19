package lt.vu.usecases;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Flower;
import lt.vu.entities.Order;
import lt.vu.entities.User;
import lt.vu.enums.OrderStatus;
import lt.vu.interceptors.LoggedInvocation;
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
public class Cart implements Serializable
{
    @Getter @Setter
    private List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

    @LoggedInvocation
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
        else orderInfos.add(new OrderInfo(flower, amount));

        return "flowerDetails?faces-redirect=true&flowerId=" + flower.getId();
    }
}
