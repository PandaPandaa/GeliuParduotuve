package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Flower;
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

    @Getter @Setter
    private Flower flowerToAdd = new Flower();

    @Getter
    private List<Flower> allFlowers;

    @PostConstruct
    public void init(){ loadAllFlowers(); }

    @Transactional
    public String AddFlower(){
        this.flowersDAO.persist(flowerToAdd);
        return "admin?faces-redirect=true";
    }

    private void loadAllFlowers(){
        this.allFlowers = flowersDAO.loadAll();
    }
}
