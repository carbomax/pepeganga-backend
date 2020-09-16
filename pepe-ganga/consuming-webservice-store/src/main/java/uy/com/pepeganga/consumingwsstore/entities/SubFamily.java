package uy.com.pepeganga.consumingwsstore.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "subfamily")
public class SubFamily implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3578114223188114511L;

	@EmbeddedId
	private SubFamilyPK subFamilyPK;

	public SubFamily(Short id, String description) {
		super();
		this.subFamilyPK = new SubFamilyPK(id, description);
	}

	public Short getId() {
		return this.subFamilyPK.getId();
	}

	public void setId(Short id) {
		this.subFamilyPK.setId(id);
	}

	public String getDescription() {
		return this.subFamilyPK.getDescription();
	}

	public void setDescription(String description) {
		this.subFamilyPK.setDescription(description);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subFamilyPK == null) ? 0 : subFamilyPK.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubFamily other = (SubFamily) obj;
		if (subFamilyPK == null) {
			if (other.subFamilyPK != null)
				return false;
		} else if (!subFamilyPK.equals(other.subFamilyPK))
			return false;
		return true;
	}

}
