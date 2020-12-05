package uy.com.pepeganga.consumingwsstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import uy.com.pepeganga.business.common.entities.*;

import java.util.List;

@Repository
public interface IBrandRepository extends JpaRepository<Brand, Short>{
}
