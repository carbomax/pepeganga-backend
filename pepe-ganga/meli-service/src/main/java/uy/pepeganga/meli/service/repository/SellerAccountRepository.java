package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.SellerAccount;

import java.util.List;

@Repository
public interface SellerAccountRepository extends JpaRepository<SellerAccount, Integer> {

    @Transactional(readOnly = true)
    boolean existsByUserId(Long userId);

    @Transactional
    SellerAccount findByUserId(Long userId);

    @Transactional(readOnly = true)
    @Query(value = "select count(*) from selleraccount s where s.id <> :accountId and s.user_id_bss = :userId", nativeQuery = true)
    Integer existsAnotherAccountWithThisUserId(Integer accountId, Long userId);

    @Transactional(readOnly = true)
    @Query(value = "select count(*) from detailspublicationsmeli d, selleraccount s where d.account_meli = :accountId and s.id = d.account_meli and  deleted = 0", nativeQuery = true)
    Integer existsPublication(Integer accountId);


    SellerAccount findByUserIdBss(Long userIdBss);

    @Query(value = "select s from SellerAccount s where s.userIdBss is not null and s.userIdBss > 0")
    List<SellerAccount> findAllBySynchronizedAccount();

}
