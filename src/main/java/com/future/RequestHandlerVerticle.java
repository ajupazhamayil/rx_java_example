package com.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;

import java.util.logging.Logger;


public class RequestHandlerVerticle extends AbstractVerticle {

    static Logger log = Logger.getLogger(RequestHandlerVerticle.class.getName());

    private HttpServer server;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        log.info(String.format("Starting the %s verticle",
                RequestHandlerVerticle.class.getName()));
        server = vertx.createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello, How are you?");
            log.info(String.format("INFO: Request %s has been served", req.toString()));
        });

        // Bind the server asynchronously
        // Verticle start will not wait for this
        // Verticle start consider complete only after
        // the Promise completed or failed
        server.listen(8080, res -> {
            if (res.succeeded()) {
                startPromise.complete();
            } else {
                startPromise.fail(res.cause());
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        System.out.println(String.format("Shutting down veticle : %s",
                this.getClass().getName()));
        server.close(res -> {
            if (res.succeeded()){
                stopPromise.complete();
            } else {
                stopPromise.fail(res.cause());
            }
        });
    }
}
