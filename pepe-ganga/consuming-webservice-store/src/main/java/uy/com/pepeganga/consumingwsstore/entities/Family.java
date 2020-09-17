package uy.com.pepeganga.consumingwsstore.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Short id;
	
	private String description;	

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name = "subFamily_PK")	
	List<SubFamily> subfamilies;
	
	public Short getId() {
		return id;
	}

	public void setId(Short id) {
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
