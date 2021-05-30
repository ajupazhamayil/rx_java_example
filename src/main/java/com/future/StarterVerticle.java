package com.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

import java.util.logging.Logger;

public class StarterVerticle extends AbstractVerticle {

    static Logger log = Logger.getLogger(RequestHandlerVerticle.class.getName());

    @Override
    public void start(){
        Router router = Router.router(vertx);
        vertx.deployVerticle(new RequestHandlerVerticle(router));
        vertx.deployVerticle(new CollectorVerticle(router));
        log.info("All verticles has been signalled");
    }

    @Override
    public void stop(){
        vertx.deploymentIDs().forEach(vertx::undeploy);
        System.out.println(String.format("Shutting down verticle : %s",
                this.getClass().getName()));
    }
}
