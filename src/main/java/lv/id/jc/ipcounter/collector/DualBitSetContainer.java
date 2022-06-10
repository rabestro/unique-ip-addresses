package lv.id.jc.ipcounter.collector;

import java.util.BitSet;

/**
 * An implementation of {@link IntContainer} that uses two {@link BitSet} for storing set of int numbers.
 */
public class DualBitSetContainer implements IntContainer {
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
    public long countUnique() {
        return (long) storage[0].cardinality() + storage[1].cardinality();
    }
}
