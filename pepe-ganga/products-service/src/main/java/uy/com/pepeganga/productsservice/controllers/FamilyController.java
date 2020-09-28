package uy.com.pepeganga.productsservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.pepeganga.business.common.entities.Family;
import uy.com.pepeganga.productsservice.services.FamilyService;
import uy.com.pepeganga.productsservice.services.FamilyServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FamilyController {

	private final FamilyService familyservice;

	public FamilyController(FamilyService familyservice) {
		this.familyservice = familyservice;
	}

	@GetMapping("/families")
	public ResponseEntity<List<Family>> getFamilies(){
		return new ResponseEntity<>(familyservice.getFamilies(), HttpStatus.OK);
	}
}
