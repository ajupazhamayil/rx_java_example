package com.future.objects;

import io.vertx.core.json.JsonObject;

import java.util.concurrent.atomic.AtomicInteger;

public class Phone {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    private final int id;

    private String name;

    private String origin;

    public Phone(String name, String origin) {
        this.id = COUNTER.getAndIncrement();
        this.name = name;
        this.origin = origin;
    }

    public Phone() {
        this.id = COUNTER.getAndIncrement();
    }

    public Phone(JsonObject json) {
        this.id = COUNTER.getAndIncrement();
        this.name = json.getString("name");
        this.origin = json.getString("origin");
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}