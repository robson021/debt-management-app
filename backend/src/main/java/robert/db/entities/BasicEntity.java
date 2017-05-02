package robert.db.entities;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class BasicEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	@Getter
	private Long id;

	@Column
	private String uuid = UUID.randomUUID().toString();

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		BasicEntity that = (BasicEntity) o;
		return hashCode() == that.hashCode();
	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

}
