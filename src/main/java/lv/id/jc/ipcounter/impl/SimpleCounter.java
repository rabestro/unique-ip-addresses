package lv.id.jc.ipcounter.impl;

import lv.id.jc.ipcounter.IpContainer;
import lv.id.jc.ipcounter.IpConverter;
import lv.id.jc.ipcounter.IpCounter;

import java.util.stream.Stream;

/**
 * Unique IPv4 addresses counter
 */
public record SimpleCounter(IpContainer container, IpConverter converter) implements IpCounter {

    @Override
    public long applyAsLong(Stream<String> addresses) {
        addresses.mapToInt(converter).forEach(container::add);
        return container.count();
    }
}
