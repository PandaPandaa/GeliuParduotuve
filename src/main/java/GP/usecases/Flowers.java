package GP.usecases;

import GP.entities.Flower;
import GP.enums.FlowerCategory;
import GP.persistence.FlowersDAO;
import lombok.Getter;
import lombok.Setter;
import GP.interceptors.LoggedInvocation;
import org.primefaces.model.file.UploadedFile;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Model
public class Flowers {

    @Inject
    private FlowersDAO flowersDAO;

    @Inject
    private FlowerProcessing flowerProcessing;

    @Getter @Setter
    private Flower flowerToAdd = new Flower();

    @Getter @Setter
    private UploadedFile photoFile;

    @LoggedInvocation
    public String AddFlower(){
        flowerToAdd.setFlowerPhoto(photoFile);
        return flowerProcessing.AddFlower(flowerToAdd);
    }

    @Transactional
    @LoggedInvocation
    @Transactional
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
