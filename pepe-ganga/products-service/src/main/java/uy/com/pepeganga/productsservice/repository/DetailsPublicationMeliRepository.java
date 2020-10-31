package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;

@Repository
public interface DetailsPublicationMeliRepository extends JpaRepository<DetailsPublicationsMeli, Integer> {

    @Query(value = "select * from detailspublicationsmeli dt where dt.mlpublication = ?1", nativeQuery = true)
    DetailsPublicationsMeli findByPublications(Integer idMLPublication);
}
