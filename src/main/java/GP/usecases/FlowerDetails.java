package GP.usecases;

import GP.entities.Flower;
import GP.persistence.FlowersDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
public class FlowerDetails {

    @Inject
    private FlowersDAO flowersDAO;

    @Getter @Setter
    private Flower flower;

    @Getter @Setter
    private Integer amount;

    public List<Integer> getAmountList()
    {
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i < flower.getRemainder() + 1; i++)
        {
            list.add(i);
        }
        return list;
    }

    @PostConstruct
    public void init()
    {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer flowerId = Integer.parseInt(requestParameters.get("flowerId"));
        this.flower = flowersDAO.findOne(flowerId);
    }
}
