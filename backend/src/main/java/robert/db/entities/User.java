package robert.db.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "USER")
@Getter
@Setter
@ToString
public class User extends BasicEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(name = "EMAIL", unique = true, nullable = false)
    @Pattern(regexp = ".*(^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$)")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String accountNo;

    @Column(name = "role", nullable = false)
    private Boolean adminRole = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Fee> fees = null;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Asset> assets = null;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Note> notes = null;
}
