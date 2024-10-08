package net.darkhax.tesla.api.implementation;

import net.darkhax.tesla.api.ITeslaProducer;

/**
 * Logic for a Tesla Producer that will produce an infinite amount of energy.
 */
public class InfiniteTeslaProducer implements ITeslaProducer {
    public static final InfiniteTeslaProducer INSTANCE = new InfiniteTeslaProducer();

    @Override
    public int getProducing() {
        return Integer.MAX_VALUE;
    }
}