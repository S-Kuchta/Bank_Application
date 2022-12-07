package ApplicationMenu;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;


public class Methods {

    public boolean checkEmailFormat(String email) {
        return email.contains("@") && email.contains(".");
    }

    public String currentDate() {
        Date date = new Date();
        return String.format("%2$td.%2$tm.%2$tY, %2$tH:%2$tM","", date);
    }

    public int getRandomValue(int Min, int Max) {
        // Get and return the random integer
        // within Min and Max
        return ThreadLocalRandom
                .current()
                .nextInt(Min, Max + 1);
    }

    public boolean getStringLength(String sentenceToCheck, int maxLength) {
        return sentenceToCheck.length() <= maxLength;
    }
}