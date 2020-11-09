package uy.com.pepeganga.productsservice.services;

import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.Family;
import uy.com.pepeganga.productsservice.repository.FamilyRepository;

import java.util.List;

@Service
public class FamilyServiceImpl implements FamilyService{

    private final FamilyRepository familyRepository;

    public FamilyServiceImpl(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @Override
    public List<Family> getFamilies() {
        return (List<Family>) familyRepository.findAll();
    }
}
