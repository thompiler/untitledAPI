package org.example.adapters;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.data_structures.BrokerMessage;
import org.example.ports.MessageBroker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaBroker implements MessageBroker {
    private Properties properties;

    public KafkaBroker(String configFileName) throws IOException {
        this.properties = loadConfig(configFileName);
    }

    @Override
    public void add(String topic, BrokerMessage msg) {
        String key = msg.key;
        String value = msg.value;

        try (final Producer<String, String> producer = new KafkaProducer<>(this.properties)) {
            producer.send(
                    new ProducerRecord<>(topic, key, value),
                    (event, ex) -> {
                        if (ex != null)
                            ex.printStackTrace();
                        else
                            System.out.printf("Produced event to topic %s: key = %-10s value = %s%n", topic, key, value);
                    });
        }
    }

    @Override
    public BrokerMessage remove(String topic) {
        // Add additional properties.
        this.properties.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-java-getting-started");
        this.properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        this.properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);

        try (final Consumer<String, String> consumer = new KafkaConsumer<>(this.properties)) {
            consumer.subscribe(Arrays.asList(topic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();
                    System.out.println(
                            String.format("Consumed event from topic %s: key = %-10s value = %s", topic, key, value));

                    BrokerMessage msg = new BrokerMessage(key, value);
                    return msg;
                }
            }
        }
    }

    private static Properties loadConfig(final String configFile) throws IOException {
        if (!Files.exists(Paths.get(configFile))) {
            throw new IOException(configFile + " not found.");
        }
        final Properties cfg = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            cfg.load(inputStream);
        }
        return cfg;
    }
}
