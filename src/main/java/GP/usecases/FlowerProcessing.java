package GP.usecases;

import GP.entities.Flower;
import GP.entities.Order;
import GP.enums.OrderStatus;
import GP.interceptors.LoggedInvocation;
import GP.persistence.FlowersDAO;
import GP.utilities.OrderInfo;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFile;

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

    @Getter
    @Setter
    private Flower flowerToAdd = new Flower();

    @Getter @Setter
    private UploadedFile photoFile;

    @Getter @Setter
    private Integer amount;

    @LoggedInvocation
    @Transactional
    public String AddFlower()
    {
        flowerToAdd.setFlowerPhoto(photoFile);
        flowersDAO.persist(flowerToAdd);
        return "admin?faces-redirect=true";
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
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

    @Transactional
    public void UpdateFlowerRemainder(String id, Integer amount)
    {
        if(!id.equals(""))
        {
            int i = Integer.parseInt(id);
            Flower f = flowersDAO.findOne(i);
            f.setRemainder(amount);
        }
    }
}
