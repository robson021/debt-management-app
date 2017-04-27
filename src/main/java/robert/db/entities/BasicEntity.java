package robert.db.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BasicEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
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
