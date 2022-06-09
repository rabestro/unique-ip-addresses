package lv.id.jc.ipcounter.collector;

/**
 * Collection for storing a set of int numbers.
 *
 * Implemented the minimum required number of methods
 * for solving the problem of counting unique numbers.
 */
public interface IntContainer {
    /**
     * Add an int number to the container
     *
     * @param number - integer number
     */
    void add(int number);

    /**
     * Count the unique int numbers
     *
     * @return count of unique numbers in the container
     */
    long countUnique();
}
