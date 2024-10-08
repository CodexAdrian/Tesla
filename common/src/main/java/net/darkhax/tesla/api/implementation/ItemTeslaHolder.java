package net.darkhax.tesla.api.implementation;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaField;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.api.ITeslaTicker;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class ItemTeslaHolder implements ITeslaProducer, ITeslaConsumer, ITeslaTicker {
    protected final ItemStack stack;
    protected final Container context;
    protected final DataComponentType<Integer> powerStorage;
    protected final DataComponentType<Boolean> enabled;
    protected final int emitting;
    protected final int capacity;

    public ItemTeslaHolder(ItemStack stack, Container context, DataComponentType<Integer> powerStorage, DataComponentType<Boolean> enabled, int emitting, int capacity) {
        this.stack = stack;
        this.context = context;
        this.powerStorage = powerStorage;
        this.enabled = enabled;
        this.emitting = emitting;
        this.capacity = capacity;
    }

    public int getStored() {
        return stack.getOrDefault(powerStorage, 0);
    }

    @Override
    public boolean acceptsPower() {
        return stack.getOrDefault(enabled, true);
    }

    @Override
    public int getProducing() {
        return stack.getOrDefault(enabled, true) ? Mth.clamp(getStored(), 0, emitting) : 0;
    }

    @Override
    public void tick(ITeslaField field) {
        if (acceptsPower()) {
            stack.set(powerStorage, Mth.clamp(getStored() + field.getConsumerAmount(), 0, capacity));
        } else {
            stack.set(powerStorage, Mth.clamp(getStored() - getProducing(), 0, capacity));
        }
    }
}
