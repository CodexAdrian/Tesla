package net.darkhax.tesla.api.implementation;

import net.darkhax.tesla.Tesla;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaField;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.api.ITeslaTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class SimpleTeslaField implements ITeslaField {
    List<ITeslaConsumer> consumers = new ArrayList<>();
    List<ITeslaProducer> producers = new ArrayList<>();
    List<ITeslaTicker> tickers = new ArrayList<>();

    public static SimpleTeslaField create() {
        return new SimpleTeslaField();
    }

    @Override
    public List<ITeslaProducer> getProducers() {
        return producers;
    }

    @Override
    public List<ITeslaConsumer> getConsumers() {
        return consumers;
    }

    public SimpleTeslaField addConsumer(ITeslaConsumer consumer) {
        consumers.add(consumer);
        return this;
    }

    public SimpleTeslaField addProducer(ITeslaProducer producer) {
        producers.add(producer);
        return this;
    }

    public SimpleTeslaField addItem(ItemStack stack) {
        var consumer = Tesla.CONSUMER_ITEM.find(stack, null);
        if (consumer != null) {
            addConsumer(consumer);
        }

        var producer = Tesla.PRODUCER_ITEM.find(stack, null);
        if (producer != null) {
            addProducer(producer);
        }

        var ticker = Tesla.TICKER_ITEM.find(stack, null);
        if (ticker != null) {
            addTicker(ticker);
        }

        return this;
    }

    public SimpleTeslaField addItems(Container container) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            addItem(container.getItem(i));
        }
        return this;
    }

    public SimpleTeslaField addBlock(Level level, BlockPos pos, Direction direction) {
        var producer = Tesla.PRODUCER_BLOCK.find(level, pos, direction);
        if (producer != null) {
            addProducer(producer);
        }

        var consumer = Tesla.CONSUMER_BLOCK.find(level, pos, direction);
        if (consumer != null) {
            addConsumer(consumer);
        }
        return this;
    }

    public SimpleTeslaField addEntity(Entity entity, Direction direction) {
        var producer = Tesla.PRODUCER_ENTITY.find(entity, direction);
        if (producer != null) {
            addProducer(producer);
        }

        var consumer = Tesla.CONSUMER_ENTITY.find(entity, direction);
        if (consumer != null) {
            addConsumer(consumer);
        }
        return this;
    }

    public SimpleTeslaField addTicker(ITeslaTicker ticker) {
        tickers.add(ticker);
        return this;
    }

    public void tick() {
        for (ITeslaTicker ticker : tickers) {
            ticker.tick(this);
        }
    }
}
