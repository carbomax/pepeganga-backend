package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.business.common.entities.MeliCategoryME2;

@Repository
public interface IMeliCategoryME2Repository extends JpaRepository<MeliCategoryME2, String> {

}
