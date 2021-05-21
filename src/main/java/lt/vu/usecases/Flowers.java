package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Flower;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.interfaces.FlowerProcessing;
import lt.vu.persistence.FlowersDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Flowers {

    @Inject
    private FlowersDAO flowersDAO;

    @Inject
    private FlowerProcessing flowerProcessing;

    @Getter @Setter
    private Flower flowerToAdd = new Flower();

    @Getter
    private List<Flower> allFlowers;

    @PostConstruct
    public void init(){ loadAllFlowers(); }

    @LoggedInvocation
    public String AddFlower(){
        return flowerProcessing.AddFlower(flowerToAdd);
    }

    @LoggedInvocation
    private void loadAllFlowers(){
        this.allFlowers = flowersDAO.loadAll();
    }
}
