package me.wonwoo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * Created by wonwoo on 2016. 3. 29..
 */
public class VerticleServer extends AbstractVerticle {

  public static void main(String[] args) {
    Runner.runExample(VerticleServer.class);
  }

  @Override
  public void start() throws Exception {
//    VertxOptions options = new VertxOptions();
//    options.setBlockedThreadCheckInterval(1000 * 60 * 60);
//    vertx = Vertx.vertx(options);
    vertx.deployVerticle("me.wonwoo.consumer.VerticleConsumer");
    vertx.deployVerticle("me.wonwoo.producer.VerticleProducer", new DeploymentOptions().setInstances(2).setWorker(true));
//    vertx.deployVerticle("me.wonwoo.producer.VerticleProducer", new DeploymentOptions().setInstances(2));

  }
}
