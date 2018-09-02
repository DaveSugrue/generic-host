package ie.sugrue.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Genre {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	private String			id, description;
	private int				orderSequence;

	public Genre(String id, String description, int orderSequence) {
		super();
		this.id = id;
		this.description = description;
		this.orderSequence = orderSequence;
	}

	public Genre() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}

	@Override
	public String toString() {
		return "Genre [id=" + id + ", description=" + description + ", orderSequence=" + orderSequence + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + orderSequence;
		return result;
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
		Genre other = (Genre) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (orderSequence != other.orderSequence) {
			return false;
		}
		return true;
	}

}
