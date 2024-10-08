package net.darkhax.tesla.api.implementation;

import earth.terrarium.common_storage_lib.data.DataManager;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.world.entity.Entity;

public class EntityTeslaProducer implements ITeslaProducer {
    protected final Entity entity;
    protected final DataManager<Boolean> enabledData;
    protected final int power;

    public EntityTeslaProducer(Entity entity, DataManager<Boolean> enabledData, int power) {
        this.entity = entity;
        this.enabledData = enabledData;
        this.power = power;
    }

    @Override
    public int getProducing() {
        return this.enabledData.get(this.entity) ? this.power : 0;
    }
}
