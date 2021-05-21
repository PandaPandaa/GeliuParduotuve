package GP.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "EXCEPTIONS")
@Getter @Setter
public class LogException implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DATETIME")
    private LocalDateTime date;

    @Column(name="USER_ID")
    private String user;

    @Column(name = "STACKTRACE")
    private String stackTrace;

    public LogException() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogException l = (LogException) o;
        return Objects.equals(id, l.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
