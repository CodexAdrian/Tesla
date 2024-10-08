package net.darkhax.tesla;

import earth.terrarium.common_storage_lib.lookup.BlockLookup;
import earth.terrarium.common_storage_lib.lookup.EntityLookup;
import earth.terrarium.common_storage_lib.lookup.ItemLookup;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaTicker;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;

public class Tesla {
    public static final String MOD_ID = "tesla";

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static class Consumers {
        public static final ItemLookup<ITeslaConsumer, Void> ITEM = ItemLookup.create(id("consumer_item"), ITeslaConsumer.class, Void.class);
        public static final BlockLookup<ITeslaConsumer, Direction> BLOCK = BlockLookup.create(id("consumer_block"), ITeslaConsumer.class, Direction.class);
        public static final EntityLookup<ITeslaConsumer, Direction> ENTITY = EntityLookup.create(id("consumer_entity"), ITeslaConsumer.class, Direction.class);
    }

    public static class Producers {
        public static final ItemLookup<ITeslaProducer, Void> ITEM = ItemLookup.create(id("producer_item"), ITeslaProducer.class, Void.class);
        public static final BlockLookup<ITeslaProducer, Direction> BLOCK = BlockLookup.create(id("producer_block"), ITeslaProducer.class, Direction.class);
        public static final EntityLookup<ITeslaProducer, Direction> ENTITY = EntityLookup.create(id("producer_entity"), ITeslaProducer.class, Direction.class);
    }

    public static class Tickers {
        public static final ItemLookup<ITeslaTicker, Void> ITEM = ItemLookup.create(id("ticker_item"), ITeslaTicker.class, Void.class);
    }
}