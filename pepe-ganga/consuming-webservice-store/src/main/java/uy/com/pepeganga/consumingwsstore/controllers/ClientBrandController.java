package uy.com.pepeganga.consumingwsstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.pepeganga.consumingwsstore.services.BrandRequestService;

@RestController
@RequestMapping("/api")
public class ClientBrandController {

	@Autowired
	BrandRequestService brands;
	
	@GetMapping("/store/brands")
	public void getTrademarks() {
		brands.storeBrand();
	}
}
