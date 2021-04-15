package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Flower;
import lt.vu.utilities.OrderInfo;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class Cart implements Serializable {

    @Getter @Setter
    private List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

    public String addToCart(Flower flower, Integer amount)
    {
        orderInfos.add(new OrderInfo(flower.getName(), flower.getPrice(), amount));
        return "flowerDetails?faces-redirect=true&flowerId=" + flower.getId();
    }

}
