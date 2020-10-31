package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;

import java.util.List;

public interface DetailsPublicationsMeliRepository  extends JpaRepository<DetailsPublicationsMeli, Integer> {

    @Transactional(readOnly = true)
    @Query(value = "select * from detailspublicationsmeli where account_meli in (:accountsId) ", nativeQuery = true)
    List<DetailsPublicationsMeli> findByProfileAccounts(List<Integer> accountsId, Pageable pageable);

    @Transactional(readOnly = true)
    @Query(value = "select * from detailspublicationsmeli dt where dt.mlpublication = ?1", nativeQuery = true)
    DetailsPublicationsMeli findByPublications(Integer idMLPublication);
}
