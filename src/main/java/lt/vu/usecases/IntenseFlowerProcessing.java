package lt.vu.usecases;

import lt.vu.entities.Flower;
import lt.vu.interfaces.FlowerProcessing;
import lt.vu.persistence.FlowersDAO;
import lt.vu.utilities.OrderInfo;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Alternative
@Named
@RequestScoped
public class IntenseFlowerProcessing implements Serializable, FlowerProcessing {

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
            Thread.sleep(10000);
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
