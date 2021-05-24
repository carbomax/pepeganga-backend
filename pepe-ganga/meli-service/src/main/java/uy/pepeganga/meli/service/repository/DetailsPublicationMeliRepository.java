package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;
import uy.pepeganga.meli.service.models.dto.reports.PublicationsMeli;

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

    List<DetailsPublicationsMeli> findAllByAccountMeli(Integer account_meli);

    @Query(value = "select count(*) from detailspublicationsmeli where status = 'active' and deleted = 0", nativeQuery = true)
    Long getCountActivePublications();

    @Query(value = "select count(*) from detailspublicationsmeli where status = 'active' and deleted = 0 and user_id = :sellerId", nativeQuery = true)
    Long getCountActivePublications(Long sellerId);

    @Query(value = "select d.idPublicationMeli as idMLPublication, d.sku as sku, d.status as status, d.title as title, d.flex as flex, d.lastUpgrade as lastUpgrade, d.pricePublication as pricePublication, i.stockActual as currentStock, s.businessName as accountBusinessName" +
            " from DetailsPublicationsMeli d, Item i, SellerAccount s where s.userIdBss = d.userId and i.sku = d.sku  and d.deleted <> 1 and d.id in (:ids)")
    List<PublicationsMeli> publicationReport(List<Integer> ids);

    @Query("select d1.id from DetailsPublicationsMeli d1 where d1.accountMeli in( select s1.id from SellerAccount s1 where s1.profile.id = :profileId and s1.userIdBss > 0)  and d1.deleted <> 1")
    List<Integer> findIdsByMeliAccountsOfProfileId(Integer profileId);
}
