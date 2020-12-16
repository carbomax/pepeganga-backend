package uy.pepeganga.meli.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uy.com.pepeganga.business.common.entities.SellerAccount;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;

@Component
public class MeliUtils {

    private static final Logger logger = LoggerFactory.getLogger(MeliUtils.class);

    @Value("${meli.api.expiration.token}")
    private String tokenExpiration;

    private static int TOKEN_EXPIRATION;

    @Value("${meli.api.expiration.token}")
    public void setNameStatic(String tokenExpiration){
        MeliUtils.TOKEN_EXPIRATION = Integer.parseInt(this.tokenExpiration);
    }

    /**
     * Does some thing in old style.
     * This method will be removed
     * @deprecated use {@link #isExpiredToken(SellerAccount sellerAccount)} instead. Example if(MeliUtils.isExpiredToken(object))
     */
    @Deprecated(since = "1.1")
    public static boolean validateTokenExpiration(long timeTokenExpirationInMilleSeconds){
        return DateTimeUtilsBss.getDurationOfMilleSeconds(DateTimeUtilsBss.getDateTimeAtCurrentTime().getMillis(), timeTokenExpirationInMilleSeconds ).getStandardMinutes() >= TOKEN_EXPIRATION;
    }

    public static boolean isExpiredToken(SellerAccount sellerAccount){

      try {
          long currentTime = DateTimeUtilsBss.getLongDateTimeAtCurrentTime();
          if(sellerAccount.getDateLastSynchronization() > currentTime || sellerAccount.getDateLastSynchronization() < currentTime){
              return true;
          } else {
              return DateTimeUtilsBss.getDurationOfMilleSeconds(DateTimeUtilsBss.getDateTimeAtCurrentTime().getMillis(), sellerAccount.getExpirationDate()).getStandardMinutes() <= TOKEN_EXPIRATION;
          }
      } catch (Exception e){
          logger.error(e.getMessage());
          return true;
      }


    }



}
