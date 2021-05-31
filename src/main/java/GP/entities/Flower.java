package GP.entities;

import GP.enums.FlowerCategory;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Flower.findAll", query = "select f from Flower as f")
})
@Table(name = "FLOWERS")
@Getter @Setter
public class Flower implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "REMAINDER")
    private Integer remainder;

    @Lob
    @Column(name = "FLOWER_PHOTO")
    private byte[] flowerPhoto;

    public void setFlowerPhoto(UploadedFile file)
    {
        byte[] array = new byte[file.getContent().length];
        System.arraycopy(file.getContent(),0,array,0,file.getContent().length);
        this.flowerPhoto = array;
    }

    @Column(name = "CATEGORY")
    private FlowerCategory flowerCategory;

    @Column(name = "DESCRIPTION")
    private String description;

    public Flower() {
    }

    public Flower(Integer id, String name) {
        this.setId(id);
        this.setName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return Objects.equals(id, flower.id) &&
                Objects.equals(name, flower.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
