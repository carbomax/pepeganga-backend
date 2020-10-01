package uy.com.pepeganga.business.common.utils.conversions;
import java.util.ArrayList;
import java.util.List;

import uy.com.pepeganga.business.common.models.Image;


public class ConversionClass {

	public static List<Image>separateImages(byte[] image){
		if(image == null)
			return new ArrayList<Image>();
		
		List<Image> images = new ArrayList<Image>();
		String imageS = new String(image);		
		
		String[] imageList = imageS.split("%");
		Image ima = new Image();
		
		for (String item : imageList) {
			String[] parts = item.split("$");
			ima.setId(Integer.decode(parts[0]));
			ima.setPhotos(parts[1]);
			images.add(ima);
		}
		 return images;		
	} 		
	
}
