package agh.distributed

import org.apache.zookeeper.{AddWatchMode, WatchedEvent, Watcher, ZooKeeper}
import org.apache.zookeeper.Watcher.Event

import scala.sys.process.Process
import scala.io.StdIn.readLine

class NodeWatcher(val program: String, val node: String) extends Watcher {

  private val client = new ZooKeeper("localhost:2184", 2000, this)
  client.addWatch(node, this, AddWatchMode.PERSISTENT)

  private val pb = Process(program)
  var p: Option[Process] = None

  override def process(watchedEvent: WatchedEvent): Unit = {
    watchedEvent.getType match {
      case Event.EventType.NodeCreated =>
        println(s"node ${watchedEvent.getPath} created")
        p = Option(pb.run())
      case Event.EventType.NodeDeleted =>
        println(s"node ${watchedEvent.getPath} removed")
        p.foreach(_.destroy)
      case other =>
        println(other)
    }
  }
}

object NodeWatcher {
  def main(args: Array[String]): Unit = {

    val program = args(0)
    val zWatcher = new NodeWatcher(program, "/z")

    new Thread(() => {
      while (true) {
        readLine()
        println("hi")
      }
    }).start()
  }
}
