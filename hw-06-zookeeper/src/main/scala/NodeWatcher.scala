package agh.distributed

import org.apache.zookeeper.{AddWatchMode, WatchedEvent, Watcher, ZooKeeper}
import org.apache.zookeeper.Watcher.Event

import scala.annotation.tailrec
import scala.sys.process.Process
import scala.io.StdIn.readLine

class NodeWatcher(val program: String, val node: String) extends Watcher {

  private val client = new ZooKeeper("localhost:2184", 2000, this)
  client.addWatch(node, this, AddWatchMode.PERSISTENT_RECURSIVE)

  private val processBuilder = Process(program)
  var process: Option[Process] = None

  def printTree(): Unit = {
    lazy val printTreeForNode: String => Unit = (node: String) => {
      println(node)
      client.getChildren(node, false).forEach(child => {
        printTreeForNode(s"$node/$child")
      })
    }

    if (client.exists(node, false) != null) {
      println("\n------")
      printTreeForNode(node)
      println("------\n")
    }
  }

  override def process(watchedEvent: WatchedEvent): Unit = {
    watchedEvent.getType match {
      case Event.EventType.NodeCreated =>
        println(s"node ${watchedEvent.getPath} created")
        watchedEvent.getPath match {
          case `node` => process = Option(processBuilder.run())
          case path if path startsWith node =>
            println(s"All children count: ${client getAllChildrenNumber node}")
          case other => println(s"unrecognized node: $other")
        }

      case Event.EventType.NodeDeleted =>
        if (watchedEvent.getPath == node) {
          println(s"node $node removed")
          process.foreach(_.destroy)
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
    val nodeWatcher = new NodeWatcher(program, "/z")

    new Thread(() => {

      @tailrec
      def commandReader(): Unit = {
        readLine() match {
          case "exit" => println("exit...")
          case _ =>
            nodeWatcher.printTree()
            commandReader()
        }
      }

      commandReader()
    }).start()
  }
}
