package lv.id.jc.ipv4;

import java.util.function.ToIntFunction;

/**
 * Converts the string representation of an IPv4 address to an int number.
 */
public class Ipv4ToIntConverter implements ToIntFunction<String> {

    @Override
    public int applyAsInt(String value) {
        long base = 0;
        long part = 0;

        for (int i  = 0; i < value.length(); ++i) {
            char symbol = value.charAt(i);
            if (symbol == '.') {
                base = (base << Byte.SIZE) | part;
                part = 0;
            } else {
                part = part * 10 + symbol - '0';
            }
        }
        return (int) ((base << Byte.SIZE) | part);
    }
}