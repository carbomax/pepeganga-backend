package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;

@Repository
public interface DetailsPublicationMeliRepository extends JpaRepository<DetailsPublicationsMeli, Integer> {
    @Query(value = "select d.* from detailspublicationsmeli d where d.sku = ?1 and d.account_meli = ?2 ", nativeQuery = true)
    DetailsPublicationsMeli findBySKUAndAccountId(String sku, Integer accountMeli);

    DetailsPublicationsMeli findByIdPublicationMeli(String idPublicationMeli);
}
