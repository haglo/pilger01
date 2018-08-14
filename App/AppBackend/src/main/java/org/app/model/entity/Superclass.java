package org.app.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class Superclass implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String uuid;

	private String comment;

	public Superclass() {
		this.prePersist();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@PrePersist
	public void prePersist() {
		if (getUuid() == null) {
			setUuid(UUID.randomUUID().toString());
		}
	}

	@Override
	public String toString() {
		return getClass().getName() + " [uuid=" + uuid + "]";
	}

	@Override
	public int hashCode() {
		this.id = getId();
		return this.id != null ? this.id.hashCode() : 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final Superclass other = (Superclass) obj;

		this.uuid = getUuid();
		other.uuid = other.getUuid();

		return this.uuid != null && this.uuid.equals(other.uuid);
	}
}
