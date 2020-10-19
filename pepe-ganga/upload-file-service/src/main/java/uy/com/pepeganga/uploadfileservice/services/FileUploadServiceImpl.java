package uy.com.pepeganga.uploadfileservice.services;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import uy.com.pepeganga.business.common.models.ReasonResponse;
import uy.com.pepeganga.uploadfileservice.models.MyProperties;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	
	@Autowired
	MyProperties property;

	String optionalFileName = "Almacen_de_imagenes";
	String directory = "";

	public ReasonResponse uploadFile(MultipartFile file) throws IOException {
		ReasonResponse result = new ReasonResponse();
		result.setSuccess(false);

		StringBuilder builder = buildURI(true);
		builder.append(file.getOriginalFilename());
		
		byte[] fileBytes = file.getBytes();
		Path path = Paths.get(builder.toString());
		Files.write(path, fileBytes);

		result.setReason("http://localhost:9999/pepeganga/upload/api/file/" + file.getOriginalFilename());
		result.setSuccess(true);
		return result;
	}

	public List<ReasonResponse> uploadFileList(List<MultipartFile> fileList) throws IOException {
		List<ReasonResponse> resultList = new ArrayList<>();

		for (MultipartFile file: fileList) {
			ReasonResponse result = new ReasonResponse();
			result.setSuccess(false);

			StringBuilder builder = buildURI(true);
			builder.append(file.getOriginalFilename());

			byte[] fileBytes = file.getBytes();
			Path path = Paths.get(builder.toString());
			Files.write(path, fileBytes);

			result.setReason("http://localhost:9999/pepeganga/upload/api/file/" + file.getOriginalFilename());
			result.setSuccess(true);
			resultList.add(result);
		}
		return resultList;
	}

    public byte[] getImage(String nameImage) throws IOException {
		StringBuilder builder = buildURI(false);
		builder.append(nameImage);

		Path path = Paths.get(builder.toString());
		return Files.readAllBytes(path);
	}

	public StringBuilder buildURI(boolean createDir){
		 //Build the directory router
		 StringBuilder builder = new StringBuilder();
		 builder.append(System.getProperty("user.home"));
		 builder.append(File.separator);
		 directory = (property.getFileName().equals("default") || property.getFileName().isBlank()) ? optionalFileName : property.getFileName();
		 builder.append(directory);

		 if(createDir) {
			 File folder = new File(builder.toString());
			 if (!folder.exists())
				 folder.mkdir();
		 }
		 builder.append(File.separator);
		 return builder;
	}

	public void deleteImages(List<String> imageList) throws IOException {
		for (String nameImage: imageList) {
			StringBuilder builder = buildURI(false);
			builder.append(nameImage);
			Path path = Paths.get(builder.toString());
			File file = new File(builder.toString());
			if (file.exists())
				Files.delete(path);
		}

	}

	private String getIP() throws UnknownHostException {
		 InetAddress address = InetAddress.getLocalHost();
		 // Cogemos la IP
		 byte[] bIPAddress = address.getAddress();

		// IP en formato String
		 String sIPAddress = "";

		 for (int x=0; x<bIPAddress.length; x++ ) {
			 if (x > 0) {
				 sIPAddress += ".";
			 }
			 sIPAddress += bIPAddress[x] & 255;
		 }
		return sIPAddress;
	 }

	private String getHostName() throws UnknownHostException {
		InetAddress address = InetAddress.getLocalHost();
		return address.getHostName();
	}
}
