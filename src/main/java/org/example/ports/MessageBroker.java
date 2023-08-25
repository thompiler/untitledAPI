package org.example.ports;

import org.example.data_structures.BrokerMessage;

public interface MessageBroker {
    void add(String topic, BrokerMessage message);
    BrokerMessage remove(String topic);
}
