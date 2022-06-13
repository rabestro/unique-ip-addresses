package lv.id.jc.ipcounter.collector;

import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.Stream;

import static java.util.Objects.checkIndex;

/**
 * An implementation of {@link IntContainer} that created
 * an array of {@link BitSet} for storing set of int numbers.
 */
public class BitSetContainer implements IntContainer {
    private final BitSet[] storage;
    private final int valueMask;
    private final int indexMask;
    private final int shift;

    /**
     * Create a new container with the desired configuration
     *
     * @param level - the number of leading bits of the number that we will use
     *              to determine the index of the cell with bitset.
     *              Valid values are from 1 to 16 ({@code Byte.SIZE * 2}).
     */
    public BitSetContainer(int level) {
        checkIndex(level - 1, Byte.SIZE * 2);
        valueMask = 0xFFFF_FFFF >>> level;
        indexMask = ~valueMask;

        this.shift = Integer.SIZE - level;
        this.storage = Stream.generate(BitSet::new)
                .limit(1L << level)
                .toArray(BitSet[]::new);
    }

    @Override
    public void add(int number) {
        int index = (number & indexMask) >>> shift;
        int value = 1 << (number & valueMask);
        storage[index].set(--value);
    }

    @Override
    public long countUnique() {
        return Arrays.stream(storage).mapToLong(BitSet::cardinality).sum();
    }
}
