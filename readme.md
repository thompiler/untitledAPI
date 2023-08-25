Example app that uses Docker and Kafka. It publishes messages to a topic then consumes them.

## Running the project

The app can be run in IntelliJ or built and run via the terminal:
```
gradle build
```

Run the `jar` file:
```
// Add this
```

Example output of the app running, producing, then consuming.

```
> Task :Main.main()
Hello and welcome!
----------------Produce messages----------------
Produced event to topic cats: key = catto      value = https://cat.com/cat1.png
Produced event to topic cats: key = bingo      value = https://cat.com/cat2.png

----------------Consume messages----------------
Consumed event from topic cats: key = catto      value = https://cat.com/cat1.png
Consumed event from topic cats: key = bingo      value = https://cat.com/cat2.png

BUILD SUCCESSFUL in 7s
2 actionable tasks: 1 executed, 1 up-to-date
4:50:19 PM: Execution finished ':Main.main()'.
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
