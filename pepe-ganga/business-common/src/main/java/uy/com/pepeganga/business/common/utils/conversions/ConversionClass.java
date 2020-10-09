package uy.com.pepeganga.business.common.utils.conversions;

import java.util.ArrayList;
import java.util.List;

import uy.com.pepeganga.business.common.models.Image;


public class ConversionClass {

    public static List<Image> separateImages(byte[] image) {
        if (image == null)
            return new ArrayList<>();

        List<Image> images = new ArrayList<>();
        String imageS = new String(image);
        String[] imageList = imageS.split(" ");
        
        for (int i = 0; i < imageList.length; i++) {
			Image img = new Image();
			img.setId(i + 1);
			img.setPhotos(imageList[i]);
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

}
