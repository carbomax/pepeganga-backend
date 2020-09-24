package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.repository.CrudRepository;
import uy.com.pepeganga.business.common.entities.Marketplace;

public interface MarketplaceRepository extends CrudRepository<Marketplace, Short> {
    boolean existsByName(String name);
}
