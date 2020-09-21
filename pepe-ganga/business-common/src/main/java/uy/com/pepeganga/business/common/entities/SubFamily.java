package uy.com.pepeganga.business.common.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(SubFamilyPK.class)
@Table(name = "subfamily")
public class SubFamily implements Serializable {
	
	private static final long serialVersionUID = 3578114223188114511L;

	@Id
	@Column(name="id")
	private Short id;
	
	@Id
	@Column(name="description")
	private String description;	


	public Short getId() {
		return this.id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
