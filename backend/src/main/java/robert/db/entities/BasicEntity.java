package robert.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@EqualsAndHashCode
abstract class BasicEntity {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    @Getter
    private Long id;

    @JsonIgnore
    private String uuid = UUID.randomUUID().toString();

}
