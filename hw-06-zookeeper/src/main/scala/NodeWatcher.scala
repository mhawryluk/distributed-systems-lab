package agh.distributed

import org.apache.zookeeper.{AddWatchMode, WatchedEvent, Watcher, ZooKeeper}
import org.apache.zookeeper.Watcher.Event

import scala.sys.process.Process
import scala.io.StdIn.readLine

class NodeWatcher(val program: String, val node: String) extends Watcher {

  private val client = new ZooKeeper("localhost:2184", 2000, this)
  client.addWatch(node, this, AddWatchMode.PERSISTENT_RECURSIVE)

  private val pb = Process(program)
  var p: Option[Process] = None

  override def process(watchedEvent: WatchedEvent): Unit = {
    watchedEvent.getType match {
      case Event.EventType.NodeCreated =>
        println(s"node ${watchedEvent.getPath} created")
        watchedEvent.getPath match {
          case `node` => p = Option(pb.run())
          case path if path startsWith node =>
            println(s"All children count: ${client getAllChildrenNumber node}")
          case other => println(s"unrecognized node: $other")
        }

      case Event.EventType.NodeDeleted =>
        if (watchedEvent.getPath == node) {
          println(s"node $node removed")
          p.foreach(_.destroy)
        }

      case Event.EventType.None =>

      case other =>
        println(other)
    }
  }
}

object NodeWatcher {
  def main(args: Array[String]): Unit = {

    val program = args(0)
    new NodeWatcher(program, "/z")

    new Thread(() => {
      while (true) {
        readLine()
        println("hi")
      }
    }).start()
  }
}
