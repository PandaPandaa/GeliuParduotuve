package lt.vu.utilities;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Flower;

import java.io.Serializable;

public class OrderInfo implements Serializable {

    @Getter @Setter
    private Integer flowerId;

    @Getter @Setter
    private String flowerName;

    @Getter @Setter
    private Double flowerPrice;

    @Getter @Setter
    private Integer flowerAmount;

    public OrderInfo(Flower flower, Integer amount)
    {
        this.flowerId = flower.getId();
        this.flowerName = flower.getName();
        this.flowerPrice = flower.getPrice();
        this.flowerAmount = amount;
    }

    public void IncreaseAmount(Integer diff)
    {
        this.flowerAmount += diff;
    }

    public void DecreaseAmount(Integer diff)
    {
        this.flowerAmount -= diff;
    }
}
