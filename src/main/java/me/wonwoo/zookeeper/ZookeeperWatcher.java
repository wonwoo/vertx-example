package me.wonwoo.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by wonwoo on 2016. 4. 1..
 */
public class ZookeeperWatcher implements Watcher, AsyncCallback.StringCallback {

  private ZooKeeper zooKeeper;
  private String rootNode;
  private String childNode;
  private String data;
  boolean dead = true;

  ZookeeperWatcher(String hostPort, String rootNode, String childNode, String data) throws IOException {
    this.rootNode = rootNode;
    this.childNode = childNode;
    this.data = data;
    zooKeeper = new ZooKeeper(hostPort, 6000, this);
  }

  public void make() {
    zooKeeper.create(rootNode, "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, this, "root");
  }

  @Override
  public void process(WatchedEvent event) {

  }

  @Override
  public void processResult(int rc, String path, Object ctx, String name) {
    KeeperException.Code code = KeeperException.Code.get(rc);
    if (code == KeeperException.Code.OK) {
      System.out.println(code);
      if ("root".equals(ctx)) {
        zooKeeper.create(childNode, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, this, null);
      }
    } else {
      dead = false;
      closing();
    }
  }

  public void run() {
    try {
      synchronized (this) {
        while (dead) {
          wait();
        }
      }
    } catch (InterruptedException e) {
    }
  }

  public void closing() {
    synchronized (this) {
      notifyAll();
    }
  }
}
