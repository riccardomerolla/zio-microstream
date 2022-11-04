package dev.zio.microstream

import one.microstream.storage.embedded.types.EmbeddedStorageManager
import one.microstream.storage.embedded.types.EmbeddedStorage
import zio._

final class MicroStream[A](private val store: EmbeddedStorageManager) extends AnyVal:

  def access[B](f: EmbeddedStorageManager => B): ZIO[Any, Throwable, B] = ZIO.attemptBlocking(f(store))

  private def getStore =
    access(_)

  def setRoot(newRoot: A): ZIO[Any, Throwable, A] =
    access(_.setRoot(newRoot).asInstanceOf[A])

  def storeRoot(): ZIO[Any, Throwable, Long] =
    access(_.storeRoot())

  def root(): ZIO[Any, Throwable, A] =
    access(_.root().asInstanceOf[A])

  def shutdown(): ZIO[Any, Throwable, Boolean] =
    access(_.shutdown())

  def isActive(): ZIO[Any, Throwable, Boolean] =
    access(_.isActive())

  def store(data: AnyRef): ZIO[Any, Throwable, Long] =
    access(_.store(data))

object MicroStream:
  def make[A] = ZIO.succeed(MicroStream[A](EmbeddedStorage.start))
