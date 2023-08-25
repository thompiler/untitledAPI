WIP!

## Running the project

The app can be run in IntelliJ or built and run via the terminal:
```
gradle build
```

Run the `jar` file:
```
// Add this
```

## Kafka: Create the topic

Start the services:
```
docker compose up -d
```

Create the cats topic if not already created:
```
docker compose exec broker \
  kafka-topics --create \
    --topic cats \
    --bootstrap-server localhost:9092 \
    --replication-factor 1 \
    --partitions 1
```

Check if the topic "cats" exists:

```
docker compose exec kafka \
  kafka-topics --list \
    --bootstrap-server localhost:9092

=> cats
```

## Based on

#### LM intern project
https://docs.google.com/document/d/1hk0YpUUuX5HMQXSYbNGwFmLjmbIlsF2liQeY0d2TlVc/edit?usp=sharing

#### Kafka guide
https://developer.confluent.io/get-started/java/
