package uy.com.pepeganga.uploadfileservice.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import uy.com.pepeganga.business.common.models.ReasonResponse;

public interface FileUploadService {

	ReasonResponse uploadFile(MultipartFile file, String uri) throws IOException;

	byte[] getImage(String nameImage) throws IOException;

	StringBuilder buildURI( boolean createDir);

	void deleteImages(List<String> imageList) throws IOException;
	
}
