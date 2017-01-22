package robert.db.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SimpleEntity {

    @Column(unique = true)
    private String uuid = UUID.randomUUID()
            .toString();

    @Override
    public boolean equals(Object o) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        SimpleEntity that = (SimpleEntity) o;
        return hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
