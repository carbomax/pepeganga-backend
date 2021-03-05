package uy.com.pepeganga.consumingwsstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.pepeganga.consumingwsstore.services.FamilyRequestService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ClientFamilyController {

	@Autowired
	FamilyRequestService family;
	
	@GetMapping("/store/families")
	public void storeFamilies() {
		//family.storeFamilies();
	}
	
	@GetMapping("/familiesandsubfamilies")
	public Map<String, String> getAllFamiliesSubFamilies() {
		return family.getFamiliesSubFamilies();
	}
}
