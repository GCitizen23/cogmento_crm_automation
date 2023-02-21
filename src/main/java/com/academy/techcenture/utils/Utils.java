package com.academy.techcenture.utils;

public class Utils {

    private Utils(){}

    /**
     * This method generates a String value of random numbers up to limit (inclusive)
     * @param limit
     * @return
     */
    public static String randomStringNumber(int limit){
        String result = "";
        for (int i = 0; i <= limit; i++) {
            int random = (int) (Math.random() * 10);
            result += random;
        }
        return result;
    }
}
