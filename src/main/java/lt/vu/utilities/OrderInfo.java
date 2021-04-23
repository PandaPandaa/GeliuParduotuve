package lt.vu.utilities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class OrderInfo implements Serializable {

    @Getter @Setter
    private String flowerName;

    @Getter @Setter
    private Double flowerPrice;

    @Getter @Setter
    private Integer flowerAmount;

    public OrderInfo(String name, Double price, Integer amount)
    {
        this.flowerName = name;
        this.flowerPrice = price;
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
