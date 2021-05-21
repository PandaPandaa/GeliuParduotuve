package lt.vu.interfaces;

import lt.vu.entities.Flower;
import lt.vu.utilities.OrderInfo;

import java.util.List;

public interface FlowerProcessing {

    String AddFlower(Flower flower);
    void ProcessFlowerAdding(Flower flower);
    void ReduceFlowerRemainder(List<OrderInfo> orderInfos);
    void IncreaseFlowerRemainder(List<OrderInfo> orderInfos);

}
