package lv.id.jc.ipcounter;

import java.util.function.ToIntFunction;

/**
 * Convert string representation of an IPv4 address to int.
 */
public interface IpConverter extends ToIntFunction<CharSequence> {
}
