package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uy.com.pepeganga.business.common.entities.*;

@Repository
public interface IImageRepository extends JpaRepository<Image, Integer>{

}
