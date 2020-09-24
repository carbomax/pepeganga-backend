package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.repository.CrudRepository;
import uy.com.pepeganga.business.common.entities.Family;

public interface FamilyRepository  extends CrudRepository<Family, Short> {
}
