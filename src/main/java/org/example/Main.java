package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.Message;
import org.example.adapters.KafkaBroker;
import org.example.data_structures.BrokerMessage;
import org.example.ports.MessageBroker;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!\n");

        final String topic = "cats";

        BrokerMessage[] msgs = {
            new BrokerMessage("catto", "https://cat.com/cat1.png"),
            new BrokerMessage("bingo", "https://cat.com/cat2.png"),
        };

        KafkaBroker kafkaBroker;

        try {
            // Add some messages to the Kafka Broker

            System.out.println("----------------Produce messages----------------");
            kafkaBroker = new KafkaBroker("broker.properties");
            kafkaBroker.add(topic, msgs[0]);
            kafkaBroker.add(topic, msgs[1]);

            // Remove some messages from the Kafka Broker
            System.out.println("\n----------------Consume messages----------------");
            BrokerMessage msg1 = kafkaBroker.remove(topic);
            BrokerMessage msg2 = kafkaBroker.remove(topic);
        } catch (IOException e) {
            System.out.printf("Got exception: %s\n", e);
        }
    }
}