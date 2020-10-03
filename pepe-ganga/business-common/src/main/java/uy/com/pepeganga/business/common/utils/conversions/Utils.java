package uy.com.pepeganga.business.common.utils.conversions;

public class Utils {

    public static boolean isNumeric(String str){
  try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
