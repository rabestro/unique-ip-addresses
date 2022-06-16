/**
 * The package contains the int container interface and implementations.
 * <p>
 * There are two main implementations of the interface in the package:
 * <p>
 * {@link com.yourcodereview.jegors.task1.container.LongArrayContainer}
 * is optimized for large numbers of uniformly distributed numbers.
 * In this implementation, when creating a container,
 * approximately 512MB of memory is immediately allocated.
 * <p>
 * {@link com.yourcodereview.jegors.task1.container.BitSetContainer}
 * is optimized for the case of non-uniform distribution of numbers.
 * In this implementation, memory is dynamically allocated as needed.
 */
package com.yourcodereview.jegors.task1.container;