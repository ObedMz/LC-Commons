package obed.me.lccommons.api.utils;

import java.time.Instant;

public class CommonsUtil {

    public static boolean isExpiredData(Instant expirationInstant) {
        Instant currentInstant = Instant.now();
        if(expirationInstant == null)
            return false;
        return currentInstant.isAfter(expirationInstant);
    }

}
