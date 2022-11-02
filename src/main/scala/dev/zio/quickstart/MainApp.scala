package dev.zio.quickstart

import one.microstream.storage.embedded.types.EmbeddedStorageManager
import one.microstream.storage.embedded.types.EmbeddedStorage
import zio.*

import java.util.Date

final class MicroStream(private val store: EmbeddedStorageManager) extends AnyVal:

  def access[A](f: EmbeddedStorageManager => A): ZIO[Any, Throwable, A] = ZIO.attemptBlocking(f(store))
  private def getStore =
    access(_)
  def setRoot[A](newRoot: A) =
    access(_.setRoot(newRoot))

  def storeRoot() =
    access(_.storeRoot())

  def root() =
    access(_.root())

  def shutdown() =
    access(_.shutdown())

object MicroStream:
  def make = ZIO.succeed(MicroStream(EmbeddedStorage.start))

object MainApp extends ZIOAppDefault {

  //def run = Console.printLine("Hello, World!")
  def run = for {
    m <- MicroStream.make
    _ <- m.root().debug
    _ <- m.setRoot("Hello World! @ " + new Date())
    _ <- m.storeRoot()
    _ <- m.root().debug
    _ <- m.shutdown()
  } yield ()
}
