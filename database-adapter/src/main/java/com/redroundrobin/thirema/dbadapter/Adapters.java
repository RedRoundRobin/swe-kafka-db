package com.redroundrobin.thirema.dbadapter;


import com.redroundrobin.thirema.dbadapter.utils.Consumer;
import com.redroundrobin.thirema.dbadapter.utils.Producer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;


public class Adapters {
    public static void main(String[] args) {
       try {
           Database timescale = new Database("jdbc:postgresql://localhost:3456/timescale", "user", "user");
           Database postgre = new Database("jdbc:postgresql://localhost:6543/postgre", "user", "user");
           Consumer consumerTimescale = new Consumer(Pattern.compile("^gw_.*"), "localhost:29092");
           Consumer consumerPostgre = new Consumer(Pattern.compile("^gw_.*"), "localhost:29092");
           Producer producer = new Producer("alerts", "localhost:29092");
           //Creazione threads
           DataInserter inserter = new DataInserter(timescale, consumerTimescale);
           DataFilter filter = new DataFilter(postgre, consumerPostgre, producer);
           //Creazione executor
           Executor executor = Executors.newCachedThreadPool();
           //Scheduling ed esecuzione dei threads
           System.out.println("Scheduling runnables");
           executor.execute(inserter);
           executor.execute(filter);
           System.out.println("Done scheduling");
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}
