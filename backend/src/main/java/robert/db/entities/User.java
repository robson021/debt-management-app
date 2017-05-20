package robert.db.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User extends BasicEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surname;

	@Column(name = "EMAIL", unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column
	private String accountNo;

	@Column
	private Boolean role = false;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Fee> fees;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Asset> assets = null;

	public void addAsset(Asset asset) {
		if ( this.assets == null ) {
			this.assets = new HashSet<>(4);
		}
		this.assets.add(asset);
	}

	@Override
	public String toString() {
		return "User{" + "name='" + name + '\'' + ", surname='" + surname + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + '}';
	}
}
