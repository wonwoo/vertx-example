package me.wonwoo.zookeeper;

import java.io.IOException;

/**
 * Created by wonwoo on 2016. 4. 1..
 */
public class ZookeeperExample {

  public static void main(String[] args) throws IOException {
    String host = "wonwoo.ml:18813,wonwoo.ml:18815,wonwoo.ml:18819";
    String rootNode = "/zk_test";
    String childNode = "/zk_test/netty2";
    String data = "http://localhost:9091";
    ZookeeperWatcher zookeeperWatcher = new ZookeeperWatcher(host, rootNode, childNode, data);
    zookeeperWatcher.make();
    zookeeperWatcher.run();

  }
}
