package com.redroundrobin.thirema.dbadapter.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

import static org.apache.kafka.server.quota.ClientQuotaEntity.ConfigEntityType.CLIENT_ID;

public class Consumer {
    private Pattern topics;
    private org.apache.kafka.clients.consumer.Consumer<Long, String> consumer;

    public Consumer(Pattern topics, String boostrapServers) {
        this.topics = topics;

        final Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, CLIENT_ID.toString());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        org.apache.kafka.clients.consumer.Consumer<Long, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(topics);
        this.consumer = consumer;
    }

    public void close(){
        System.out.println("[Consumer] Closed");
        consumer.close();
    }

    public List<JsonObject> fetchMessages() {
        System.out.println("[Consumer] Started");

        List<JsonObject> devicesData = new ArrayList<>();

        final ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofSeconds(4));

        if (consumerRecords.count() == 0) {
            System.out.println("[Consumer] Zero messages found");
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