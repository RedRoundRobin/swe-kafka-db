package com.redroundrobin.thirema.kafkadatacollector;


import com.redroundrobin.thirema.kafkadatacollector.utils.Consumer;
import com.redroundrobin.thirema.kafkadatacollector.utils.Database;
import com.redroundrobin.thirema.kafkadatacollector.utils.Producer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class KafkaDataCollectorApp {

   public static void main(String[] args) {
       Logger logger = Logger.getLogger(KafkaDataCollectorApp.class.getName());
       try {
           String kafkaBoostrapServers = "localhost:29092";

           Database timescale = new Database("jdbc:postgresql://localhost:3456/timescale", "user", "user");
           Database postgre = new Database("jdbc:postgresql://localhost:6543/postgre", "user", "user");
           Consumer consumerInserter = new Consumer(Pattern.compile("^gw_.*"), kafkaBoostrapServers);
           Consumer consumerFilter = new Consumer(Pattern.compile("^gw_.*"), kafkaBoostrapServers);
           DataInserter inserter;
           DataFilter filter;
           try (Producer producer = new Producer("alerts", kafkaBoostrapServers)) {
               //Creazione threads
               inserter = new DataInserter(timescale, consumerInserter);
               filter = new DataFilter(postgre, consumerFilter, producer);
           }
           //Creazione executor
           Executor executor = Executors.newCachedThreadPool();
           //Scheduling ed esecuzione dei threads
           logger.info("Scheduling runnables");
           executor.execute(inserter);
           executor.execute(filter);
           logger.info("Done scheduling");
       } catch (Exception e) {
           logger.log(Level.SEVERE, "Exception occur!", e);
       }
    }
}
