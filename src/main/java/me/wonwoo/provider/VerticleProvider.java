package me.wonwoo.provider;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by wonwoo on 2016. 3. 29..
 */
public class VerticleProvider extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.get("/").handler(this::getOk);
    vertx.createHttpServer().requestHandler(router::accept).listen(8080);
  }

  private void getOk(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    MultiMap params = routingContext.request().params();
    String id = params.get("id");
    JsonObject code = new JsonObject().put("code", "000").put("id", id);
    routingContext.vertx().eventBus().send("tcp.push.message", code);
    response.putHeader("content-type", "application/json").end(code.toString());
  }
}
