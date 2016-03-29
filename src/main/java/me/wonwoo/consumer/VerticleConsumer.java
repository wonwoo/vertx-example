package me.wonwoo.consumer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by wonwoo on 2016. 3. 29..
 */
public class VerticleConsumer extends AbstractVerticle {

  private NetServer server;
  private Map<String, NetSocket> userInfo = new HashMap<>();

  @Override
  public void start() throws Exception {
    server = vertx.createNetServer();

    server.connectHandler(socket ->
      socket.handler(buffer -> userInfo.put(buffer.toString(Charset.defaultCharset()).replaceAll("(\r)?\n", ""), socket))
    );

    server.close(socket -> userInfo.remove(socket));

    EventBus eventBus = vertx.eventBus();

    eventBus.consumer("tcp.push.message", message -> {
      JsonObject body = (JsonObject) message.body();
      NetSocket socket = userInfo.get(body.getString("id"));
      socket.write(body.toString());
    });

//    eventBus.consumer("tcp.push.message", message -> {
//      JsonObject body = (JsonObject) message.body();
//      NetSocket socket = userInfo.get(body.getString("id"));
//      socket.write(body.toString());
//    });


    server.listen(8888);
  }
}