package com.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import rx.Completable;
import rx.Single;

import java.util.logging.Logger;


public class CollectorVerticle extends AbstractVerticle {

    static Logger log = Logger.getLogger(CollectorVerticle.class.getName());

    public CollectorVerticle() {
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info(String.format("Starting verticle: %s",
                this.getClass().getName()));
        startPromise.complete();
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        System.out.println(String.format("Shutting down verticle : %s",
                this.getClass().getName()));
        stopPromise.complete();
    }

}
