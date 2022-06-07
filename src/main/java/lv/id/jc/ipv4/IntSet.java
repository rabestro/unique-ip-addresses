package lv.id.jc.ipv4;

import java.util.BitSet;

/**
 * Collection for storing a set of int numbers
 *
 * Implemented the minimum required number of methods
 * for solving the problem of counting unique numbers.
 */
public class IntSet {
    private final BitSet[] storage = {new BitSet(), new BitSet()};

    /**
     * Add a number to a Collection
     *
     * @param i - number to add
     */
    public void add(int i) {
        if (i >= 0) {
            storage[0].set(i);
        } else {
            storage[1].set(-++i);
        }
    }

    /**
     * Collection size
     *
     * @return collection size (number of unique ints).
     */
    public long size() {
        return (long) storage[0].cardinality() + storage[1].cardinality();
    }
}
