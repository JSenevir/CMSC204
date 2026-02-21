/**
 * Represents a simple time-driven simulation.
 */
public interface SimulationInterface {

    /**
     * Advance the simulation by exactly one minute.
     * This may involve:
     *  - Adding new orders to the queue
     *  - Shipping one order
     *  - Moving late orders to the returns stack
     */
    void tick();

    /**
     * Returns true if:
     *  - All orders have been released into the queue, AND
     *  - The queue is empty (all orders shipped).
     */
    boolean isFinished();

    /** Returns the current simulation time in minutes. */
    int getCurrentMinute();

    /** Returns the total number of orders that have arrived. */
    int getTotalArrived();

    /** Returns the total number of orders that have been shipped. */
    int getTotalShipped();

    /** Returns the total number of orders that shipped late. */
    int getTotalLate();
}
