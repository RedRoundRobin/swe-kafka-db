package com.redroundrobin.thirema.dbadapter;


import com.redroundrobin.thirema.dbadapter.utils.Consumer;
import com.redroundrobin.thirema.dbadapter.utils.Database;
import com.redroundrobin.thirema.dbadapter.utils.Producer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class AdaptersApplication {

    private static final Logger logger = Logger.getLogger(AdaptersApplication.class.getName());
    public static void main(String[] args) {
       try {

           String kafkaBoostrapServers = "localhost:29092";

           Database timescale = new Database("jdbc:postgresql://localhost:3456/timescale", "user", "user");
           Database postgre = new Database("jdbc:postgresql://localhost:6543/postgre", "user", "user");
           Consumer consumerTimescale = new Consumer(Pattern.compile("^gw_.*"), kafkaBoostrapServers);
           Consumer consumerPostgre = new Consumer(Pattern.compile("^gw_.*"), kafkaBoostrapServers);
           DataInserter inserter;
           DataFilter filter;
           try (Producer producer = new Producer("alerts", kafkaBoostrapServers)) {
               //Creazione threads
               inserter = new DataInserter(timescale, consumerTimescale);
               filter = new DataFilter(postgre, consumerPostgre, producer);
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
