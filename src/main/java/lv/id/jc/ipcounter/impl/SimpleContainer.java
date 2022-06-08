package lv.id.jc.ipcounter.impl;

import lv.id.jc.ipcounter.IpContainer;

import java.util.BitSet;

/**
 * A simple container for storing a set of IPv4 addresses
 */
public class SimpleContainer implements IpContainer {
    private final BitSet[] storage = {new BitSet(), new BitSet()};

    @Override
    public void add(int i) {
        if (i >= 0) {
            storage[0].set(i);
        } else {
            storage[1].set(-++i);
        }
    }

    @Override
    public long count() {
        return (long) storage[0].cardinality() + storage[1].cardinality();
    }
}
