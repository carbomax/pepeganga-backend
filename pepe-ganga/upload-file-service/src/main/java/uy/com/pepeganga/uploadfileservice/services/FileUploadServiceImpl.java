package uy.com.pepeganga.uploadfileservice.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import uy.com.pepeganga.uploadfileservice.models.MyProperties;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	
	@Autowired
	MyProperties property;

	public String uploadFile(MultipartFile file) throws IOException {
		
		String optionalFileName = "Almacen_de_imagenes";
		
		//Build the directory router
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.home"));
		builder.append(File.separator);
		String directory = (property.getFileName().equals("default") || property.getFileName().isBlank()) ? optionalFileName : property.getFileName();
		
		File folder = new File(builder.toString());
		if(!folder.exists())
			folder.mkdir();
		
		builder.append(directory);		
		builder.append(File.separator);
		builder.append(file.getOriginalFilename());
		
		byte[] fileBytes = file.getBytes();
		Path path = Paths.get(builder.toString());
		Files.write(path, fileBytes);
		
		return new String(builder.toString());
	}
}
