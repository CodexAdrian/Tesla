package net.darkhax.tesla.api.implementation;

import earth.terrarium.common_storage_lib.data.DataManager;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.function.Supplier;

public class TeslaHolder implements ITeslaConsumer, ITeslaProducer {
    protected Object dataHolder;
    protected DataManager<Integer> powerData;
    protected Supplier<Boolean> enabledSupplier;
    protected int emitPower;
    protected int capacity;

    public static TeslaHolder block(BlockEntity blockEntity, DataManager<Integer> powerData, Property<Boolean> enabledSupplier, int emitPower, int capacity) {
        return new TeslaHolder(blockEntity, powerData, () -> blockEntity.getBlockState().getValue(enabledSupplier), emitPower, capacity);
    }

    public static TeslaHolder entity(Entity entity, DataManager<Integer> powerData, DataManager<Boolean> enabledData, int emitPower, int capacity) {
        return new TeslaHolder(entity, powerData, () -> enabledData.get(entity), emitPower, capacity);
    }

    protected TeslaHolder(Object dataHolder, DataManager<Integer> powerData, Supplier<Boolean> enabledSupplier, int emitPower, int capacity) {
        this.dataHolder = dataHolder;
        this.powerData = powerData;
        this.enabledSupplier = enabledSupplier;
        this.emitPower = emitPower;
        this.capacity = capacity;
    }

    public int getStored() {
        return this.powerData.get(this.dataHolder);
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int insert(int power, boolean simulate) {
        int inserted = Math.min(this.powerData.get(this.dataHolder), power);
        if (!simulate) {
            this.powerData.set(this.dataHolder, this.powerData.get(this.dataHolder) - inserted);
        }
        return inserted;
    }

    public int extract(int power, boolean simulate) {
        int extracted = Math.min(this.powerData.get(this.dataHolder), power);
        if (!simulate) {
            this.powerData.set(this.dataHolder, this.powerData.get(this.dataHolder) + extracted);
        }
        return extracted;
    }

    @Override
    public boolean acceptsPower() {
        return true;
    }

    @Override
    public int getProducing() {
        return this.enabledSupplier.get() ? emitPower : 0;
    }
}
