package uy.com.pepeganga.uploadfileservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import uy.com.pepeganga.business.common.models.ReasonResponse;
import uy.com.pepeganga.uploadfileservice.services.FileUploadService;

import javax.activation.FileTypeMap;
import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UploadFileController {
	
	@Autowired
	FileUploadService fileService;

	@PostMapping("/file/upload-file")
	public ResponseEntity<ReasonResponse> uploadFile(@RequestBody MultipartFile image){
		if(image == null || image.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format("Archivo no válido"));
		}		
		try {
			return new ResponseEntity<ReasonResponse>(fileService.uploadFile(image), HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Error almacenando archivo %s", e.getMessage()));
		}		
	}

	@PostMapping("/file/upload-filelist")
	public ResponseEntity<List<ReasonResponse>> uploadFileList(@RequestBody ArrayList<MultipartFile> imageList){
		for (MultipartFile image: imageList) {
			if(image == null && image.isEmpty()){
				throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Usted ha subido archivos no validos"));
			}
		}
		try {
			return new ResponseEntity<List<ReasonResponse>>(fileService.uploadFileList(imageList), HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Error almacenando archivos %s", e.getMessage()));
		}
	}

	@GetMapping("/file/{name}")
	public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) {
		try {
			byte[] image = fileService.getImage(name);

			StringBuilder builder = fileService.buildURI(false);
			builder.append(name);
			File img = new File(builder.toString());

			return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(image);
		}
		catch (Exception e){
			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Error obteniendo imagen del servidor", e.getMessage()));
		}
	}

	@DeleteMapping("/file-delete/{nameList}")
	public ResponseEntity<?> deleteImage(@PathVariable("nameList") List<String> nameList) {
		try {
			fileService.deleteImages(nameList);
			return new ResponseEntity(HttpStatus.OK);
		}
		catch (Exception e){
			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Error obteniendo imagen del servidor", e.getMessage()));
		}
	}

}
