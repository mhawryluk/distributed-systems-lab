package agh.distributed

import org.apache.zookeeper.{AddWatchMode, WatchedEvent, Watcher, ZooKeeper}
import org.apache.zookeeper.Watcher.Event

class ZWatcher(val program: String, val node: String) extends Watcher {

  val zk = new ZooKeeper("localhost:2184", 2000, this)
  zk.addWatch(node, this, AddWatchMode.PERSISTENT)

  override def process(watchedEvent: WatchedEvent): Unit = {
    println(watchedEvent.getState)

    watchedEvent.getType match {
      case Event.EventType.NodeCreated =>
        println(s"node ${watchedEvent.getPath} created")
      case Event.EventType.NodeDeleted =>
        println(s"node ${watchedEvent.getPath} removed")
      case other =>
        println(other)
    }
  }
}

object ZWatcher {
  def main(args: Array[String]): Unit = {

    val program = args(0)
    val zWatcher = new ZWatcher(program, "/z")

    new Thread(() => {
      while (true) {

      }
    }).start()
  }
}
