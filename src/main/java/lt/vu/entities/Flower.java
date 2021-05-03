package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;
import lt.vu.enums.FlowerCategory;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Flower.findAll", query = "select f from Flower f")
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

    @Column(name = "FLOWER_PHOTO")
    private byte[] flowerPhoto;

    @Column(name = "CATEGORY")
    private FlowerCategory flowerCategory;

    public Flower() {
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
