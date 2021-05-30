package com.future;

import com.future.objects.Phone;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PhoneVerticle extends AbstractVerticle {

    static Logger log = Logger.getLogger(RequestHandlerVerticle.class.getName());

    private Router router;
    private HttpServer server;
    private Map<Integer, Phone> products = new LinkedHashMap();

    public PhoneVerticle(Router router){
        this.router = router;
    }

    @Override
    public void start(){
        server = vertx.createHttpServer();
        log.info(String.format("Starting the %s verticle",
                this.getClass().getName()));
        createSomeData();
        router.get("/api/phones").handler(this::getAll);
        // To give the read body capability
        router.route("/api/phones*").handler(BodyHandler.create());
        router.post("/api/phones").handler(this::addOne);
        router.delete("/api/phones/:id").handler(this::deleteOne);
    }

    private void getAll(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(products.toString());
    }

    private void addOne(RoutingContext routingContext) {
        final Phone phone = new Phone(routingContext.getBodyAsJson());
        products.put(phone.getId(), phone);
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(products.toString());
    }

    private void deleteOne(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            Integer idAsInteger = Integer.valueOf(id);
            products.remove(idAsInteger);
        }
        routingContext.response().setStatusCode(204).end();
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

    private void createSomeData() {
        Phone iphone = new Phone("iphone", "Scotland, Islay");
        products.put(iphone.getId(), iphone);
        Phone samsung = new Phone("samsung", "Scotland, Island");
        products.put(samsung.getId(), samsung);
    }
}
