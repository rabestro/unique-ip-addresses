package com.yourcodereview.jegors.task1.container;

import java.util.Arrays;

/**
 * An implementation of {@link IntContainer} optimized to store a huge amount of int numbers.
 *
 * For this purpose, we immediately allocate an array of long numbers as a bit set.
 * The added numbers are divided into two parts. The first 26 bits are used to get
 * the index in the array, and the remaining 6 bits represent a value from 0 to 63.
 * This value is used to set the corresponding bit in the long number.
 */
public class LongArrayContainer implements IntContainer {
    private static final int VALUE_SIZE = 6;
    private static final int VALUE_MASK = 0x3F;
    private static final int STORAGE_SIZE = 1 << (Integer.SIZE - VALUE_SIZE + 1);

    private final long[] storage = new long[STORAGE_SIZE];

    @Override
    public void add(final int number) {
        final var index = number >>> VALUE_SIZE;
        final var value = 1L << (number & VALUE_MASK);
        storage[index] |= value;
    }

    @Override
    public long countUnique() {
        return Arrays.stream(storage).map(Long::bitCount).sum();
    }
}
