package com.github.naferx

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Source}
import akka.stream.testkit.scaladsl.{TestSink, TestSource}
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._

final class MaxStageSpec extends FlatSpec with Matchers {

  implicit val system = ActorSystem("testing")
  implicit val materializer = ActorMaterializer.create(system)


  "MaxStage" should "just send the max number" in {
    val (ups, down) = TestSource.probe[Int]
      .via(new MaxStage)
      .toMat(TestSink.probe)(Keep.both)
      .run()

    ups.sendNext(10)
    down.request(1)

    down.expectNext(10)
    down.request(1)
    ups.sendNext(9)
    ups.sendNext(8)

    down.expectNoMsg(200.millis)
    ups.sendNext(11)


    down.expectNext(11)
    ups.sendNext(17)

    ups.sendComplete()


    down.expectNoMsg(200.millis)
    down.request(1)

    down.expectNext(17)
    down.expectComplete()
  }

}
