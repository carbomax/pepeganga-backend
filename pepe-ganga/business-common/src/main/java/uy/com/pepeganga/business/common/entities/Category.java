package uy.com.pepeganga.business.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "category")
public class Category implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7440614458606026312L;

	@Id	
	@Column(name="id")
	private Short id;
	
	@Column(name="description")
	private String description;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Category)) return false;
		Category category = (Category) o;
		return Objects.equals(id, category.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
