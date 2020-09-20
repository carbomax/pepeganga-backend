package uy.com.pepeganga.productsservice.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "family")
public class Family implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8039538839590766050L;

	@Id	
	@Column(name="id")
	private Short id;
	
	@Column(name="description")
	private String description;	

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name = "family_id")	
	private List<SubFamily> subfamilies;
	
	public Short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SubFamily> getSubfamilies() {
		return subfamilies;
	}

	public void setSubfamilies(List<SubFamily> subfamilies) {
		this.subfamilies = subfamilies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Family other = (Family) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}