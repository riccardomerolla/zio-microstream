package dev.zio.microstream

import dev.zio.microstream.demo._
import zio.*
import zio.test.TestAspect.*
import zio.test.*

/**
 * Created by Riccardo Merolla on 04/11/22.
 */
object MicrostreamSpec extends ZIOSpecDefault {

  val element = new MyData("Foo")

  def spec: Spec[TestEnvironment, Any] =
    suite("MicrostreamSpec") {
      test("make and shutdown") {
        ZIO.scoped {
          for {
            micro    <- MicroStream.make[MyRoot]
            shutdown <- micro.shutdown()
          } yield assertTrue(shutdown)
        }
      } +
        test("println root") {
          ZIO.scoped {
            for {
              micro    <- MicroStream.make[MyRoot]
              _        <- micro.setRoot(new MyRoot())
              root     <- micro.root()
              _        <- Console.printLine(root.myObjects)
              shutdown <- micro.shutdown()
            } yield assertTrue(shutdown)
          }
        } +
        test("store") {
          ZIO.scoped {
            for {
              micro    <- MicroStream.make[MyRoot]
              _        <- micro.setRoot(new MyRoot())
              root     <- micro.root()
              _        <- {
                root.myObjects.add(element)
                micro.store(root.myObjects)
              }
              _        <- Console.printLine(root.myObjects)
              shutdown <- micro.shutdown()
            } yield assertTrue(1 == root.myObjects.size())
          }
        }
    }

}
