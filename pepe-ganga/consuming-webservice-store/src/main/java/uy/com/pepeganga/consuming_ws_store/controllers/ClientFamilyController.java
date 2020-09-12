package uy.com.pepeganga.consuming_ws_store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.consuming_ws_store.services.FamilyService;
import uy.com.pepeganga.consuming_ws_store.wsdl.families.CargaFamiliasExecuteResponse;

@RestController
@RequestMapping("/api")
public class ClientFamilyController {

	@Autowired
	FamilyService family;
	
	@GetMapping("/families")
	public CargaFamiliasExecuteResponse getFamilies() {
		return family.getFamilies();
	}
}
