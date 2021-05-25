package GP.utilities;

import GP.entities.Flower;
import GP.interceptors.LoggedInvocation;
import GP.persistence.FlowersDAO;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import java.io.*;
import java.util.concurrent.CompletableFuture;

@Named
@RequestScoped
public class FileProcessing implements Serializable {

    @Inject
    private FlowersDAO flowersDAO;

    @Transactional
    public byte[] getFlowerPictureById(String id) {
        if(!id.equals(""))
        {
            int i = Integer.parseInt(id);
            Flower a = flowersDAO.findOne(i);
            return a.getFlowerPhoto();
        }
        return new byte[]{};
    }
}