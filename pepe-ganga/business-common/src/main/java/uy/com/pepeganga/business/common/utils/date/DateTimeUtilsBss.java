package uy.com.pepeganga.business.common.utils.date;

import org.joda.time.*;

import java.util.TimeZone;


public class DateTimeUtilsBss {

    private  DateTimeUtilsBss() {
        // Do nothing
    }

    public static DateTime getDateTimeAtCurrentTime(){
        return LocalDate.now(DateTimeZone.forTimeZone(TimeZone.getTimeZone("UYST"))).toDateTimeAtCurrentTime();
    }

    public static long plusCurrentTimeMilleSeconds(long plus, DateTimePlusType plusType){
        long result;
            switch (plusType) {
                case HOUR:
                    result = DateTimeUtilsBss.getDateTimeAtCurrentTime().getMillis() + plus * 24 *  60  * 1000 ;
                    break;
                case MINUTE:
                    result = DateTimeUtilsBss.getDateTimeAtCurrentTime().getMillis() +   plus * 60  * 1000;
                    break;
                case SECOND:
                    result = DateTimeUtilsBss.getDateTimeAtCurrentTime().getMillis() +  plus * 1000;
                    break;
                default:
                    result = DateTimeUtilsBss.getDateTimeAtCurrentTime().getMillis();
                    break;
            }
            return  result;
    }


    public static Duration getDurationOfMilleSeconds(long start, long end){
        Duration duration = new Duration(start, end);
        Instant.now().plus(duration);
        return duration;
    }


}

