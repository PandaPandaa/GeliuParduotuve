package GP.usecases;

import GP.entities.Flower;
import GP.interceptors.LoggedInvocation;
import GP.utilities.OrderInfo;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@LoggedInvocation
@RequestScoped
public class CartController implements Serializable
{
    @Inject
    private Cart cart;

    public String addToCart(Flower flower, Integer amount)
    {
        if(cart.getOrderInfos().stream().anyMatch(o -> o.getFlowerName().equals(flower.getName())))
        {
            OrderInfo orderInfo = cart.getOrderInfos().stream().filter(o -> o.getFlowerName().equals(flower.getName())).findFirst().get();
            if(orderInfo.getFlowerAmount() + amount > flower.getRemainder())
            {
                return "flowerDetails?faces-redirect=true&flowerId=" + flower.getId() + "&error=not-enough-remainder";
            }
            else orderInfo.IncreaseAmount(amount);
        }
        else cart.getOrderInfos().add(new OrderInfo(flower, amount));

        return "flowerDetails?faces-redirect=true&flowerId=" + flower.getId();
    }

    public String removeFromCart(Flower flower)
    {
        if(cart.getOrderInfos().stream().anyMatch(o -> o.getFlowerName().equals(flower.getName())))
        {
            OrderInfo orderInfo = cart.getOrderInfos().stream().filter(o -> o.getFlowerName().equals(flower.getName())).findFirst().get();
            cart.getOrderInfos().remove(orderInfo);
        }
        return "flowerDetails?faces-redirect=true&flowerId=" + flower.getId();
    }
}
