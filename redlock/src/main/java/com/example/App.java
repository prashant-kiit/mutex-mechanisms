package com.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static Integer NO_OF_INSTANCES = 20;
    private static Integer QUORUM = NO_OF_INSTANCES / 2 + 1;
    private static Counter[] counters = new Counter[NO_OF_INSTANCES];
    private static String RESOURCE_KEY = "Resource-";
    private static Integer EXPIRATION_DURATION_SECONDS = 10;

    static {
        for (int i = 0; i < NO_OF_INSTANCES; i++) {
            counters[i] = new Counter("Counter-" + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            try {
                Jedis jedis = new Jedis("localhost", 6379, 5000);
                SetParams setParams = new SetParams();
                setParams.nx();
                setParams.ex(EXPIRATION_DURATION_SECONDS);
                List<Counter> results = new ArrayList<Counter>();

                while (true) {
                    for (Counter counter : counters) {
                        String result = jedis.set(RESOURCE_KEY + counter.getName(), Thread.currentThread().getName(),
                                setParams);
                        if (!"OK".equals(result)) {
                            results.add(counter);
                        } else {
                            System.out.println(
                                    "Locked acquired by " + Thread.currentThread().getName() + " on Counter "
                                            + counter.getName());
                        }
                    }

                    if (results.size() >= QUORUM) {
                        for (Counter counter : results) {
                            jedis.del(RESOURCE_KEY + counter.getName());
                            jedis.set(RESOURCE_KEY + counter.getName(), Thread.currentThread().getName(), setParams);
                            System.out.println(
                                    "Locked acquired by " + Thread.currentThread().getName() + " on Counter "
                                            + counter.getName());
                        }
                        for (Counter counter : counters) {
                            performCriticalSection(counter);
                            jedis.del(RESOURCE_KEY + counter.getName());
                            System.out.println("Locked released by " + Thread.currentThread().getName()
                                    + " on Counter " + counter.getName());
                        }
                        jedis.close();
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        for (Counter counter : counters) {
            System.out.println(counter.getCount());
        }

    }

    private static void performCriticalSection(Counter counter) throws InterruptedException {
        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            counter.increment();
        }
        Thread.sleep(1000);
    }
}
