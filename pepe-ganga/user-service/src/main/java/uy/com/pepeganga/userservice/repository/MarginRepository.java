package uy.com.pepeganga.userservice.repository;


import org.springframework.data.repository.CrudRepository;
import uy.com.pepeganga.business.common.entities.Margin;

public interface MarginRepository extends CrudRepository<Margin, Short> {
}
