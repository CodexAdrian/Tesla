package net.darkhax.tesla.api;

/**
 * Interface for items that have ticking behavior while in an inventory.
 */
public interface ITeslaTicker {

    /**
     * Called every tick for the item. For producers, you should use this to consume fuel and set the amount of power
     * being emitted. Producers should largely ignore the context parameter, as in a read only system the amount of power
     * being emitted should be constant. Consumers should, if they are storing power, use this to consume power and increase
     * the stored power count. Context is provided to calculate the amount of power a consumer should accept.
     */
    void tick(ITeslaField field);
}
