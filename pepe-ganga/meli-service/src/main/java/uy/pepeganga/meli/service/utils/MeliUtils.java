package uy.pepeganga.meli.service.utils;

import org.springframework.beans.factory.annotation.Value;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;

public class MeliUtils {

    @Value("${expiration-token}")
    private String tokenExpiration;

    private static int TOKEN_EXPIRATION;

    public static boolean validateTokenExpiration(long timeTokenExpirationInMilleSeconds){
        return DateTimeUtilsBss.getDurationOfMilleSeconds(DateTimeUtilsBss.getDateTimeAtCurrentTime().getMillis(), timeTokenExpirationInMilleSeconds ).getStandardMinutes() >= MeliUtils.TOKEN_EXPIRATION;
    }

    @Value("${name}")
    public void setNameStatic(String tokenExpiration){
        MeliUtils.TOKEN_EXPIRATION = Integer.parseInt(this.tokenExpiration);
    }

}
