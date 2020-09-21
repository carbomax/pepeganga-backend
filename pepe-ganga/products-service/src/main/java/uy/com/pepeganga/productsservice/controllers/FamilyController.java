package uy.com.pepeganga.productsservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.pepeganga.productsservice.services.FamilyServiceImpl;

@RestController
@RequestMapping("/api")
public class FamilyController {

	private final FamilyServiceImpl familyservice;

	public FamilyController(FamilyServiceImpl familyservice) {
		this.familyservice = familyservice;
	}

}
