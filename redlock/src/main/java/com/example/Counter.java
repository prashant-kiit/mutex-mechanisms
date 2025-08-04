package com.example;

public class Counter {
    private String name = "";
    private int counter = 0;

    public Counter(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getCount() {
        return this.counter;
    }

    public void increment() {
        counter++;
        System.out.println(Thread.currentThread().getName() + " => " + counter);
    }
}
