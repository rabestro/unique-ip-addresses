package com.yourcodereview.jegors.task1.converter;

import java.util.function.ToIntFunction;

public class MkyongConverter implements ToIntFunction<String> {

    @Override
    public int applyAsInt(String ipAddress) {
        var ipAddressInArray = ipAddress.split("\\.");
        long result = 0L;

        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);
        }

        return (int) result;
    }
}