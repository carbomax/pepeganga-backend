package uy.com.pepeganga.productsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uy.com.pepeganga.business.common.entities.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>{

}
