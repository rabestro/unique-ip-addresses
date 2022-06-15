package com.yourcodereview.jegors.task1.converter;

/**
 * A simple converter to transform an IPv4 address from string representation to int.
 */
public class IPv4SimpleConverter implements IPv4Converter {
    private static final int DECIMAL_BASE = 10;

    /**
     * @param ipAddress representation of an IPv4 address
     * @return int number representing the given address
     */
    @Override
    public int applyAsInt(CharSequence ipAddress) {
        long base = 0;
        long part = 0;

        for (int i = 0; i < ipAddress.length(); ++i) {
            char symbol = ipAddress.charAt(i);
            if (symbol == '.') {
                base = (base << Byte.SIZE) | part;
                part = 0;
            } else {
                part = part * DECIMAL_BASE + symbol - '0';
            }
        }
        return (int) ((base << Byte.SIZE) | part);
    }

}
