package uy.com.pepeganga.business.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "brand")
public class Brand implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8579951759996328352L;

	@Id	
	@Column(name="id")
	private Short id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="marcaInUse")
	private String marcaInUse;

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

	public String getMarcaInUse() {
		return marcaInUse;
	}

	public void setMarcaInUse(String marcaInUse) {
		this.marcaInUse = marcaInUse;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Brand)) return false;
		Brand brand = (Brand) o;
		return Objects.equals(id, brand.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
