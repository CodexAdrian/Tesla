package net.darkhax.tesla.api;

public interface ITeslaProducer {

    /**
     * Gets the amount of power the Tesla block is currently emitting.
     * @return The amount of power being emitted.
     */
    int getProducing();
}