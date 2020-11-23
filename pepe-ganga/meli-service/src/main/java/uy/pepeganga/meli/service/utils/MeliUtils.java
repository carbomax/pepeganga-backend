package uy.pepeganga.meli.service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;

@Component
public class MeliUtils {

    @Value("${meli.api.expiration.token}")
    private String tokenExpiration;

    private static int TOKEN_EXPIRATION;

    public static boolean validateTokenExpiration(long timeTokenExpirationInMilleSeconds){
        return DateTimeUtilsBss.getDurationOfMilleSeconds(DateTimeUtilsBss.getDateTimeAtCurrentTime().getMillis(), timeTokenExpirationInMilleSeconds ).getStandardMinutes() >= TOKEN_EXPIRATION;
    }

    @Value("${meli.api.expiration.token}")
    public void setNameStatic(String tokenExpiration){
        MeliUtils.TOKEN_EXPIRATION = Integer.parseInt(this.tokenExpiration);
    }

}
