package com.future;

import io.vertx.core.AbstractVerticle;

import java.util.logging.Logger;

public class StarterVerticle extends AbstractVerticle {

    static Logger log = Logger.getLogger(RequestHandlerVerticle.class.getName());

    @Override
    public void start(){
        vertx.deployVerticle(new RequestHandlerVerticle());
        vertx.deployVerticle(new CollectorVerticle());
        log.info("All verticles has been signalled");
    }

    @Override
    public void stop(){
        vertx.deploymentIDs().forEach(vertx::undeploy);
        System.out.println(String.format("Shutting down verticle : %s",
                this.getClass().getName()));
    }
}
