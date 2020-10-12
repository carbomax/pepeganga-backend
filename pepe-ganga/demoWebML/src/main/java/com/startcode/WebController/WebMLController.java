package com.startcode.WebController;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.startcode.EcommerceServices.MercadoLibreEcommerce;
import com.startcode.Models.User;
import com.startcode.Models.UserTestRequest;

import io.swagger.client.ApiException;
import io.swagger.client.model.AccessToken;
import io.swagger.client.model.UserResponse;
import io.swagger.client.model.UserTestResponse;
import jdk.jfr.BooleanFlag;

@RestController
@RequestMapping("/MercadoLibre")
public class WebMLController {

	@Autowired
	MercadoLibreEcommerce ml;
	/*
	@GetMapping
	public ResponseEntity<String> test(){
		return new ResponseEntity<>(ml.test(), HttpStatus.OK);
	}*/

	@GetMapping("/Autheticate")
	public String Authenticate() throws UnsupportedEncodingException, ApiException {
		return MercadoLibreEcommerce.getAuthUrl();
	}
	
	@GetMapping(path = "/AccessToken/{code}")
	public AccessToken getAccessToken(@PathVariable("code") String code) throws UnsupportedEncodingException, ApiException{
		return MercadoLibreEcommerce.getAccessToken(code);
	}
	
	@PostMapping("/CreateUserTest")
	public UserTestResponse CreateTestUser(@RequestBody UserTestRequest testUser) throws ApiException{
		return ml.CreateTestUser(testUser);		
	}
	
	@GetMapping(path = "/GetUserAccountInformation")
	public UserResponse getUserAccountInformation(@RequestParam Map<String, ?> customQuery) throws UnsupportedEncodingException, ApiException{
		return ml.getUserAccountInfo(customQuery);
	}
}
