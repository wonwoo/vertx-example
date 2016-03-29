package me.wonwoo;

import io.vertx.core.AbstractVerticle;
import me.wonwoo.consumer.VerticleConsumer;
import me.wonwoo.producer.VerticleProducer;

/**
 * Created by wonwoo on 2016. 3. 29..
 */
public class VerticleServer extends AbstractVerticle{

  public static void main(String[] args) {
    Runner.runExample(VerticleServer.class);
  }

  @Override
  public void start() throws Exception {
    vertx.deployVerticle(new VerticleConsumer());
    vertx.deployVerticle(new VerticleProducer());
  }
}
