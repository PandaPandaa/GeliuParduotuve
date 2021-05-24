package GP.utilities;

import GP.entities.Flower;
import GP.interceptors.LoggedInvocation;
import GP.persistence.FlowersDAO;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.cdi.GraphicImageBean;

import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import java.io.*;

@GraphicImageBean
public class FileProcessing implements Serializable {

    @Inject
    private FlowersDAO flowersDAO;

    @Getter @Setter
    private boolean uploaded = false;

    @Getter @Setter
    private byte[] pic;

    @Getter @Setter
    private Part part;

    private final String limit_type_file = "gif|jpg|png|jpeg";

    @LoggedInvocation
    public void uploadPhoto()
    {
        System.out.println("upload");
        byte[] fileContent = new byte[]{};
        if (part.getSize() > 0) {
            String fileName = getFilename(part);
            if (checkFileType(fileName)) {
                try {
                    fileContent = new byte[(int) part.getSize()];
                    InputStream in = part.getInputStream();
                    in.read(fileContent);
                }catch (IOException ignored) { }
            }
        }
        uploaded = true;
        this.pic = fileContent;
    }

    private String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

    private boolean checkFileType(String fileName) {
        if (fileName.length() > 0) {
            String[] parts = fileName.split("\\.");
            if (parts.length > 0) {
                String extention = parts[parts.length - 1];
                return this.limit_type_file.contains(extention);
            }
        }
        return false;
    }

    public void flush()
    {
        uploaded = false;
        pic = new byte[]{};
    }

    @Transactional
    public byte[] getFlowerPictureById(String id) {
        int i = Integer.parseInt(id);
        Flower a = flowersDAO.findOne(i);
        return a.getFlowerPhoto();
    }
}