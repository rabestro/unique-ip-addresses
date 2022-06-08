package lv.id.jc.ipcounter;

/**
 * Collection for storing a set of IPv4 addresses.
 *
 * Implemented the minimum required number of methods
 * for solving the problem of counting unique numbers.
 */
public interface IpContainer {

    /**
     * Add an IPv4 address to the container
     *
     * @param ip - IPv4 address in int format
     */
    void add(int ip);

    /**
     * Count the unique IPv4 addresses
     *
     * @return number of unique IPv4 addresses in the container
     */
    long count();
}
