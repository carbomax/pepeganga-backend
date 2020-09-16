package uy.com.pepeganga.consumingwsstore.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.consumingwsstore.services.FamilyService;
import uy.com.pepeganga.consumingwsstore.wsdl.families.CargaFamiliasExecuteResponse;

@RestController
@RequestMapping("/api")
public class ClientFamilyController {

	@Autowired
	FamilyService family;
	
	@GetMapping("/families")
	public CargaFamiliasExecuteResponse getFamilies() {
		return family.getFamilies();
	}
	
	@GetMapping("/familiesandsubfamilies")
	public Map<String, String> getAllFamiliesSubFamilies() {
		return family.getFamiliesSubFamilies();
	}
}
