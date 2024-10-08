package net.darkhax.tesla.api.implementation;

import net.darkhax.tesla.api.ITeslaConsumer;

/**
 * Logic for a Tesla Consumer that will consume infinite amounts of power.
 */
public class InfiniteTeslaConsumer implements ITeslaConsumer {
    public static final InfiniteTeslaConsumer INSTANCE = new InfiniteTeslaConsumer();

    @Override
    public boolean acceptsPower() {
        return true;
    }
}