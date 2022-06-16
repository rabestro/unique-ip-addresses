package com.yourcodereview.jegors.task1.container;

import java.util.BitSet;

import static java.util.Arrays.stream;
import static java.util.Objects.checkIndex;
import static java.util.stream.Stream.generate;

/**
 * An implementation of {@link IntContainer} that created
 * an array of {@link BitSet} for storing set of int numbers.
 * <p>
 * This implementation allocates the necessary memory dynamically, as needed.
 * Therefore, if we have a set of IP addresses from only one or several
 * specific subnets, this implementation can give a significant gain in memory usage.
 */
public class BitSetContainer implements IntContainer {
    private final BitSet[] storage;
    private final int mask;
    private final int shift;

    /**
     * Create a new container with the desired configuration.
     *
     * @param level - the number of leading bits of the number that we will use
     *              to determine the index of the cell with bitset.
     *              Valid values are from 1 to 16 ({@code Byte.SIZE * 2}).
     * @throws IndexOutOfBoundsException if level outside the range 1..16
     */
    public BitSetContainer(int level) {
        checkIndex(level - 1, Byte.SIZE * 2);

        mask = 0xFFFF_FFFF >>> level;
        shift = Integer.SIZE - level;
        storage = generate(BitSet::new).limit(1L << level).toArray(BitSet[]::new);
    }

    @Override
    public void add(int ip) {
        storage[ip >>> shift].set(ip & mask);
    }

    @Override
    public long countUnique() {
        return stream(storage).mapToLong(BitSet::cardinality).sum();
    }
}
