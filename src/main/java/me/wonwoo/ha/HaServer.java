package me.wonwoo.ha;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

import java.lang.management.ManagementFactory;

/**
 * Created by wonwoo on 2016. 3. 29..
 */
public class HaServer extends AbstractVerticle{

  public static void main(String[] args) {
    Launcher.main(new String[] { "run", HaServer.class.getName(), "-ha"});
  }

  @Override
  public void start() throws Exception {
    vertx.createHttpServer().requestHandler(req -> {
      final String name = ManagementFactory.getRuntimeMXBean().getName();
      req.response().end("Happily served by " + name);
    }).listen(8080);
  }
}
