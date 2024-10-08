package net.darkhax.tesla.api;

/**
 * Interface for objects that consume Tesla power. Consumer should only ever be null if the
 * object is not capable of receiving power, like if the face is disabled on a Tesla block.
 * It should, however, never be null if the object is capable of receiving power but is not
 * currently receiving any or is full.
 */
public interface ITeslaConsumer {

    /**
     * Determines if the consumer can accept power.
     * @return Whether the consumer can accept power.
     */
    boolean acceptsPower();
}
