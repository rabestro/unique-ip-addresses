package com.yourcodereview.jegors.task1.converter;

import java.util.function.ToIntFunction;
import java.util.regex.Pattern;

public class Mkyong2Converter implements ToIntFunction<String> {
    private static final Pattern PATTERN_DOT = Pattern.compile(".", Pattern.LITERAL);

    @Override
    public int applyAsInt(String ipAddress) {
        var ipAddressInArray = PATTERN_DOT.split(ipAddress);

        long result = 0L;

        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);
        }

        return (int) result;
    }
}