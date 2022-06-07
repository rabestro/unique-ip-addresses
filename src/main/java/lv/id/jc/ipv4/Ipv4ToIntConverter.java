package lv.id.jc.ipv4;

import java.util.function.ToIntFunction;

public class Ipv4ToIntConverter implements ToIntFunction<String> {

    @Override
    public int applyAsInt(String value) {
        long base = 0;
        long part = 0;

        for (int i  = 0; i < value.length(); ++i) {
            var symbol = value.charAt(i);
            if (symbol == '.') {
                base = (base << 8) | part;
                part = 0;
            } else {
                part = part * 10 + symbol - '0';
            }
        }
        return (int) ((base << 8) | part);
    }
}