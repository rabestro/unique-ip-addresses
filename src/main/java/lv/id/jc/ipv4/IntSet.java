package lv.id.jc.ipv4;

import java.util.BitSet;

public class IntSet {
    private final BitSet[] storage = {new BitSet(Integer.MAX_VALUE), new BitSet(Integer.MAX_VALUE)};

    public void add(int i) {
        if (i > 0) {
            storage[0].set(i);
        } else {
            storage[1].set(-++i);
        }
    }

    public long size() {
        return (long) storage[0].cardinality() + storage[1].cardinality();
    }

}
