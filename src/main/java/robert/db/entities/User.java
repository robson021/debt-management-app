package robert.db.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.CollectionUtils;

@Entity
@Table(name = "USER")
public class User extends SimpleEntity {

    @Transient
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = //
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Transient
    private static final Pattern VALID_PASSWORD_REGEX = //
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Asset> assets = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if ( !VALID_EMAIL_ADDRESS_REGEX.matcher(email)
                .find() ) {
            throw new Exception("Invalid email");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if ( !VALID_PASSWORD_REGEX.matcher(password)
                .find() ) {
            throw new Exception("Invalid password");
        }
        this.password = password;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    public void addAsset(Asset asset) {
        if ( CollectionUtils.isEmpty(this.assets) ) {
            this.assets = new HashSet<>(1);
        }
        this.assets.add(asset);
    }

}
