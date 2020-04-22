package com.redroundrobin.thirema.kafkadatacollector.utils;

import static org.apache.kafka.server.quota.ClientQuotaEntity.ConfigEntityType.CLIENT_ID;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class Consumer implements AutoCloseable {
  private final org.apache.kafka.clients.consumer.Consumer<Long, String> kafkaConsumer;

  private static final Logger logger = CustomLogger.getLogger(Consumer.class.getName(),
      Level.WARNING);

  public Consumer(Pattern topics, String bootstrapServers) {
    final Properties properties = new Properties();

    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, CLIENT_ID.toString());
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

    org.apache.kafka.clients.consumer.Consumer<Long, String> consumer = new KafkaConsumer<>(properties);
    consumer.subscribe(topics);
    this.kafkaConsumer = consumer;
  }

  public Consumer(Pattern topics, String bootstrapServers, String groupId) {
    final Properties properties = new Properties();

    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

    org.apache.kafka.clients.consumer.Consumer<Long, String> consumer = new KafkaConsumer<>(properties);
    consumer.subscribe(topics);
    this.kafkaConsumer = consumer;
  }

  @Override
  public void close() {
    logger.log(Level.INFO, "[Consumer] Closed");
    kafkaConsumer.close();
  }

  public List<JsonObject> fetchMessages() {
    logger.log(Level.INFO, "[Consumer] Started");

    List<JsonObject> devicesData = new ArrayList<>();

    final ConsumerRecords<Long, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(4));

    if (consumerRecords.count() == 0) {
      logger.log(Level.INFO, "[Consumer] Zero messages found");
      return devicesData;
    }


    for (ConsumerRecord<Long, String> record : consumerRecords) {
      if (System.currentTimeMillis() - record.key() <= 7500) {
        for (JsonElement data : JsonParser.parseString(record.value()).getAsJsonArray()) {
          JsonObject deviceData = data.getAsJsonObject();
          devicesData.add(deviceData);
        }
      }
    }

    return devicesData;
  }
}
