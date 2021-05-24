package GP.usecases;

import GP.interfaces.FlowerProcessing;
import GP.entities.Flower;
import GP.persistence.FlowersDAO;
import GP.utilities.OrderInfo;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Alternative
@Named
@RequestScoped
public class InstantFlowerProcessing implements Serializable, FlowerProcessing {

    @Inject
    private FlowersDAO flowersDAO;

    public String AddFlower(Flower flower)
    {
        CompletableFuture.runAsync(() -> ProcessFlowerAdding(flower));

        return "admin?faces-redirect=true&message=addingflower";
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void ProcessFlowerAdding(Flower flower) {
        try{
            Thread.sleep(1000);
        } catch (InterruptedException ignored){
        }
        flowersDAO.persist(flower);
    }

    @Transactional
    public void ReduceFlowerRemainder(List<OrderInfo> orderInfos)
    {
        for (OrderInfo o:
                orderInfos) {
            Flower flower = flowersDAO.findOne(o.getFlowerId());
            flower.setRemainder(flower.getRemainder() - o.getFlowerAmount());
            flowersDAO.update(flower);
        }
    }

    @Transactional
    public void IncreaseFlowerRemainder(List<OrderInfo> orderInfos)
    {
        for (OrderInfo o:
                orderInfos) {
            Flower flower = flowersDAO.findOne(o.getFlowerId());
            flower.setRemainder(flower.getRemainder() + o.getFlowerAmount());
            flowersDAO.update(flower);
        }
    }
}
