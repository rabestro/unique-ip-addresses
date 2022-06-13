package lv.id.jc.ipcounter.converter;

import java.util.function.ToIntFunction;

/**
 * Convert string representation of an IPv4 address to int.
 */
public interface IPv4Converter extends ToIntFunction<CharSequence> {
}
