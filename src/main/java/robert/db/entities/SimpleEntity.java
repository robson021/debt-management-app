package robert.db.entities;

import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SimpleEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String uuid = UUID.randomUUID()
            .toString();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
