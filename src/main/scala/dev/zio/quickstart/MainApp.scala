package dev.zio.quickstart

import one.microstream.storage.embedded.types.EmbeddedStorageManager
import one.microstream.storage.embedded.types.EmbeddedStorage
import zio.*

import java.util.Date

final class MicroStream(private val store: EmbeddedStorageManager) extends AnyVal:

  def access[A](f: EmbeddedStorageManager => A): ZIO[Any, Throwable, A] = ZIO.attemptBlocking(f(store))

  private def getStore =
    access(_)

  def setRoot[A](newRoot: A): ZIO[Any, Throwable, A] =
    access(_.setRoot(newRoot).asInstanceOf[A])

  def storeRoot(): ZIO[Any, Throwable, Long] =
    access(_.storeRoot())

  def root[A](): ZIO[Any, Throwable, A] =
    access(_.root().asInstanceOf[A])

  def shutdown(): ZIO[Any, Throwable, Boolean] =
    access(_.shutdown())

  def isActive(): ZIO[Any, Throwable, Boolean] =
    access(_.isActive())

object MicroStream:
  def make = ZIO.succeed(MicroStream(EmbeddedStorage.start))

object MainApp extends ZIOAppDefault {

  //def run = Console.printLine("Hello, World!")
  def run = for {
    m <- MicroStream.make
    _ <- m.root[String]().debug
    _ <- m.setRoot[String]("Hello World! @ " + new Date())
    _ <- m.storeRoot().debug
    _ <- m.root[String]().debug
    _ <- m.shutdown().debug
    _ <- m.isActive().debug
  } yield ()
}
