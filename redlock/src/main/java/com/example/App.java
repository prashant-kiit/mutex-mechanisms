package com.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

public class App {
    private static Counter counter = new Counter();
    private static String RESOURCE_1_KEY = "resource_1";
    private static Integer EXPIRATION_DURATION_SECONDS = 10;

    public static void main(String[] args) {
        Runnable task = () -> {
            Jedis jedis = new Jedis("localhost", 6379);
            try {
                SetParams setParams = new SetParams();
                setParams.nx();
                setParams.ex(EXPIRATION_DURATION_SECONDS);
                while (true) {
                    String result = jedis.set(RESOURCE_1_KEY, Thread.currentThread().getName(), setParams);
                    if ("OK".equals(result)) {
                        System.out.println("Locked acquired by " + Thread.currentThread().getName());
                        performCriticalSection(counter);
                        jedis.del(RESOURCE_1_KEY);
                        jedis.close();
                        System.out.println("Locked released by " + Thread.currentThread().getName());
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        };

        Thread thread1 = new Thread(task, "Thread1");
        Thread thread2 = new Thread(task, "Thread2");

        thread1.start();
        thread2.start();
    }

    private static void performCriticalSection(Counter counter) throws InterruptedException {
        Thread.sleep(1000);
        counter.increment();
        Thread.sleep(1000);
    }
}
