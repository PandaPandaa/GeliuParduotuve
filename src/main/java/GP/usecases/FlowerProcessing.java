package GP.usecases;

import GP.entities.Flower;
import GP.persistence.FlowersDAO;
import GP.utilities.OrderInfo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class FlowerProcessing implements Serializable {

    @Inject
    private FlowersDAO flowersDAO;

    @Transactional
    public String AddFlower(Flower flower)
    {
        flowersDAO.persist(flower);
        return "admin?faces-redirect=true&message=addingflower";
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
