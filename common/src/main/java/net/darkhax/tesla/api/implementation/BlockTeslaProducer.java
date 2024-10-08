package net.darkhax.tesla.api.implementation;

import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockTeslaProducer implements ITeslaProducer {
    protected final BlockState state;
    protected final Property<Boolean> enabled;
    protected final int power;

    public BlockTeslaProducer(BlockState state, Property<Boolean> enabled, int power) {
        this.state = state;
        this.enabled = enabled;
        this.power = power;
    }

    @Override
    public int getProducing() {
        return state.getValue(enabled) ? power : 0;
    }
}
