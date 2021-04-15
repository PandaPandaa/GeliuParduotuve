package lt.vu.utilities;

import java.io.Serializable;

public class OrderInfo implements Serializable {

    private String flowerName;

    private Double flowerPrice;

    private Integer flowerAmount;

    public OrderInfo(String name, Double price, Integer amount)
    {
        this.flowerName = name;
        this.flowerPrice = price;
        this.flowerAmount = amount;
    }
}
