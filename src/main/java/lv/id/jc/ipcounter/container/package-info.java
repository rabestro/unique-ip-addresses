/**
 * The package contains the int container interface and implementations.
 * <p>
 * There are two main implementations of the interface in the package:
 * <p>
 * {@link lv.id.jc.ipcounter.container.LongArrayContainer}
 * is optimized for large numbers of uniformly distributed numbers.
 * In this implementation, when creating a container,
 * approximately 512MB of memory is immediately allocated.
 * <p>
 * {@link lv.id.jc.ipcounter.container.BitSetContainer}
 * is optimized for the case of non-uniform distribution of numbers.
 * In this implementation, memory is dynamically allocated as needed.
 */
package lv.id.jc.ipcounter.container;