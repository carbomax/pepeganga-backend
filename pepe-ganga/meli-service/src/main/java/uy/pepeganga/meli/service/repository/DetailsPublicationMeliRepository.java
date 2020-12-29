package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;

import java.util.List;

@Repository
public interface DetailsPublicationMeliRepository extends JpaRepository<DetailsPublicationsMeli, Integer> {
    @Query(value = "select d.* from detailspublicationsmeli d where d.sku = ?1 and d.account_meli = ?2 ", nativeQuery = true)
    DetailsPublicationsMeli findBySKUAndAccountId(String sku, Integer accountMeli);

    DetailsPublicationsMeli findByIdPublicationMeli(String idPublicationMeli);

    boolean existsByIdPublicationMeli(String idPublicationMeli);

    @Transactional(readOnly = true)
    @Query(value = "select dt.* from detailspublicationsmeli dt where account_meli in (:accountsId) and dt.deleted = :isDeleted and dt.margin = :idMargin ", nativeQuery = true)
    List<DetailsPublicationsMeli> findByIdAccountsAndMargin(List<Integer> accountsId, Short idMargin, Integer isDeleted);

    List<DetailsPublicationsMeli> findAllBySku(String sku);

    @Query(value = "select count(*) from detailspublicationsmeli where status = 'active' and deleted = 0", nativeQuery = true)
    Long getCountActivePublications();
}
