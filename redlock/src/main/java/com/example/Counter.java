package com.example;

public class Counter {
    private int counter = 0;

    public void increment() {
        counter++;
        System.out.println(Thread.currentThread().getName() + " => " + counter);
    }
}
