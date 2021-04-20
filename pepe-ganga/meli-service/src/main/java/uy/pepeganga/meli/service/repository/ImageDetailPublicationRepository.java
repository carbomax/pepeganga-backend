package uy.pepeganga.meli.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;
import uy.com.pepeganga.business.common.entities.ImagePublicationMeli;

import java.util.List;

@Repository
public interface ImageDetailPublicationRepository extends JpaRepository<ImagePublicationMeli, Integer > {

    @Modifying
    @Transactional
    @Query(value = "delete from imagepublicationmeli ipm where ipm.id in (:ids)", nativeQuery = true)
    void deleteByIds(List<Integer> ids);
}
