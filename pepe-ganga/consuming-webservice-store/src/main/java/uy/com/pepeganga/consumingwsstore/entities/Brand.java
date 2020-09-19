package uy.com.pepeganga.consumingwsstore.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Brand other = (Brand) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
