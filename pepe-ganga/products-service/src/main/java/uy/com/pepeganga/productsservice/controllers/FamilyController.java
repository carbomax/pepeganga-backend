package uy.com.pepeganga.productsservice.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.productsservice.services.FamilyService;

@RestController
@RequestMapping("/api")
public class FamilyController {

	@Autowired
	FamilyService familyservice;
	
	@RequestMapping("/allfamilies")
	public Map<String, String> getAllFamiliesSubFamilies(){
		return familyservice.getAllFamiliesSubFamilies();
	}
}
