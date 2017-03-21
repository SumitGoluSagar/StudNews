package org.mistu.android.studnews.Utils;

/**
 * Created by kedee on 14/3/17.
 */
public class TimeUtils {

    public static long getMinutesDifference(long timeStart,long timeStop){
        long diff = timeStop - timeStart;
        long diffMinutes = diff / (60 * 1000);

        return  diffMinutes;
    }
}
