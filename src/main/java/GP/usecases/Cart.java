package GP.usecases;

import GP.entities.Flower;
import GP.interceptors.LoggedInvocation;
import GP.utilities.OrderInfo;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
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
