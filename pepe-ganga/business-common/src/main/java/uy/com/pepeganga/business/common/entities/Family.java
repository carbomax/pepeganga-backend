package uy.com.pepeganga.business.common.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


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

	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "family_id")	
	private List<SubFamily> subfamilies = null;
	
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Family)) return false;
		Family family = (Family) o;
		return Objects.equals(id, family.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
