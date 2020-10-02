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
        String[] imageList = imageS.split("%%");
        for (String item : imageList) {
            Image ima = new Image();
            String[] parts = item.split("¿¿");
            ima.setId(Integer.decode(parts[0]));
            ima.setPhotos(parts[1]);
            images.add(ima);
        }
        return images;
    }

}
