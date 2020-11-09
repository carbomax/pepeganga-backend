package uy.com.pepeganga.business.common.utils.conversions;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import uy.com.pepeganga.business.common.entities.Image;


public class ConversionClass {

    public static List<Image> separateImages(byte[] image) {
        if (image == null)
            return new ArrayList<>();

        List<Image> images = new ArrayList<>();
        String imageS = new String(image);
        String[] imageList = imageS.split(" ");
        
        for (int i = 0; i < imageList.length; i++) {
			Image img = new Image();			
			img.setPhotos(imageList[i]);
			img.setOrder(0);
			img.setTitle("");
			images.add(img);
		}
        return images;
    }
    
    public static byte[] joinImages(List<Image> images)
    {
    	StringBuilder photo = new StringBuilder();
		for (Image ima : images) {
			if(ima != null)			
				photo.append(ima.getPhotos().trim()).append(" ");			
		}			
		return photo.toString().getBytes();		
    }

    public static String decodeBase64ToString(String encode64) {    	
		byte[] valueDecoded = Base64.getDecoder().decode(encode64);
		String value = new String(valueDecoded);
		return value;
    }
   
    public static Integer decodeBase64ToInt(String encode64) {    	
		byte[] valueDecoded = Base64.getDecoder().decode(encode64);
		String value = new String(valueDecoded);
		Integer decode = Integer.valueOf(value);
		return decode;
    }
}
