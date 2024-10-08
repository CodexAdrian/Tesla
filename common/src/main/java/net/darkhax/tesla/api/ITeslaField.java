package net.darkhax.tesla.api;

import java.util.List;

public interface ITeslaField {
    List<ITeslaProducer> getProducers();

    List<ITeslaConsumer> getConsumers();

    default int getConsumingCount() {
        return getConsumers().stream().filter(ITeslaConsumer::acceptsPower).toList().size();
    }

    default int getConsumerAmount() {
        return getProducers().stream().mapToInt(ITeslaProducer::getProducing).sum() / getConsumingCount();
    }
}
