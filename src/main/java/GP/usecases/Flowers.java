package GP.usecases;

import GP.entities.Flower;
import GP.enums.FlowerCategory;
import GP.interfaces.FlowerProcessing;
import GP.persistence.FlowersDAO;
import GP.utilities.FileProcessing;
import lombok.Getter;
import lombok.Setter;
import GP.interceptors.LoggedInvocation;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Model
public class Flowers {

    @Inject
    private FileProcessing fileProcessing;

    @Inject
    private FlowersDAO flowersDAO;

    @Inject
    private FlowerProcessing flowerProcessing;

    @Getter @Setter
    private Flower flowerToAdd = new Flower();

    @LoggedInvocation
    public String AddFlower(){
        flowerToAdd.setFlowerPhoto(fileProcessing.getPic());
        fileProcessing.flush();
        return flowerProcessing.AddFlower(flowerToAdd);
    }

    @LoggedInvocation
    public List<Flower> loadAllFlowers(){
        return flowersDAO.loadAll();
    }

    @LoggedInvocation
    public List<Flower> getFlowersByCategory(FlowerCategory category)
    {
        return flowersDAO.loadByCategory(category);
    }

    public Map<String, FlowerCategory> getFlowerCategoryMap()
    {
        Map<String, FlowerCategory> map = new LinkedHashMap<String, FlowerCategory>();
        for (FlowerCategory c:
             FlowerCategory.values()) {
            map.put(c.name(), c);
        }
        return  map;
    }
}
