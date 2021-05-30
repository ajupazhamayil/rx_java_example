package com.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

import java.util.logging.Logger;


public class RequestHandlerVerticle extends AbstractVerticle {

    static Logger log = Logger.getLogger(RequestHandlerVerticle.class.getName());

    private final Router router;
    private HttpServer server;

    public RequestHandlerVerticle() {
        this.router = Router.router(vertx);
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        server = vertx.createHttpServer();
        Route route = router.route().path("/handler/");
        log.info(String.format("Starting the %s verticle",
                this.getClass().getName()));
        route.handler(ctx -> {
            ctx.response()
                    .putHeader("content-type", "text/plain")
                    .end(String.format("%s: Hello, How are you?",
                            this.getClass().getName()));
            log.info(String.format("INFO: Request %s has been served", ctx.toString()));
        });

        // Bind the server asynchronously
        // Verticle start will not wait for this
        // Verticle start consider complete only after
        // the Promise completed or failed
        server.requestHandler(router).listen(8080, res -> {
            if (res.succeeded()) {
                startPromise.complete();
            } else {
                startPromise.fail(res.cause());
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        System.out.println(String.format("Shutting down verticle : %s",
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
