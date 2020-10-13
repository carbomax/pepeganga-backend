package uy.com.pepeganga.uploadfileservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import uy.com.pepeganga.uploadfileservice.services.FileUploadService;

@RestController
@RequestMapping("/api")
public class UploadFileController {
	
	@Autowired
	FileUploadService fileService;

	@PostMapping("/file/upload-file")
	public ResponseEntity<String> uploadFile(@RequestBody MultipartFile image){
		if(image == null || image.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format("Archivo no válido"));
		}		
		try {			
			return new ResponseEntity<String>(fileService.uploadFile(image), HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Error almacenando archivo %s", e.getMessage()));
		}		
	}
	
	@PostMapping("/file/upload")
	public ResponseEntity<String> upload(@RequestBody String image){
		return new ResponseEntity<>(new String(), HttpStatus.OK);
		
	}
	
}
