package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;
import uy.com.pepeganga.business.common.entities.MeliOrders;

import java.util.List;

public interface DetailsPublicationsMeliRepository  extends JpaRepository<DetailsPublicationsMeli, Integer> {

    @Transactional(readOnly = true)
    @Query(value = "select * from detailspublicationsmeli where account_meli in (:accountsId) and sku like %:sku% " +
                    "and id_publication_meli like %:idMeliPublication% and status like %:typeStateSearch% and deleted = 0",
            countQuery = "select count(*) from detailspublicationsmeli where account_meli in (:accountsId) and sku like %:sku% " +
                    "and id_publication_meli like %:idMeliPublication% and status like %:typeStateSearch% and deleted = 0 ", nativeQuery = true)
    Page<DetailsPublicationsMeli> findDetailsPublicationByFilter(String sku, String idMeliPublication, List<Integer> accountsId, String typeStateSearch, Pageable pageable);

    @Transactional(readOnly = true)
    @Query(value = "select * from detailspublicationsmeli dt where dt.mlpublication = ?1", nativeQuery = true)
    DetailsPublicationsMeli findByPublications(Integer idMLPublication);

    @Query(value = "update detailspublicationsmeli dt set dt.mlpublication= null where dt.mlpublication = ?1", nativeQuery = true)
    DetailsPublicationsMeli updateMLPublicationsField(Integer idMLPublication);
}