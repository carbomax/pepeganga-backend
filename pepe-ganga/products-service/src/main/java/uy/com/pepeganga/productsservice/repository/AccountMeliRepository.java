package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;
import uy.com.pepeganga.business.common.entities.SellerAccount;

import java.util.List;

@Repository
public interface AccountMeliRepository extends JpaRepository<SellerAccount, Integer> {

    @Transactional(readOnly = true)
    @Query(value = "select * from selleraccount sa where sa.profile_id = ?1", nativeQuery = true)
    List<SellerAccount> findByIdProfile(Integer idProfile);

    @Transactional(readOnly = true)
    @Query(value = "select * from selleraccount s where s.user_id = ?1", nativeQuery = true)
    SellerAccount findByUserId(Long userId);
}
