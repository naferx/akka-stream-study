package com.github.naferx

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Source}
import akka.stream.testkit.scaladsl.TestSink
import org.scalatest.{FlatSpec, Matchers}


final class RandomNumberSourceSpec extends FlatSpec with Matchers {

  implicit val system = ActorSystem("testing")
  implicit val materializer = ActorMaterializer.create(system)


  "RandomNumberSource" should "produce a random number" in {
    val source = new RandomNumberSource()

    val (_, sub) = Source.fromGraph(source).toMat(TestSink.probe)(Keep.both).run()

    sub.request(2)
    sub.expectNextPF {
      case x => x shouldBe a [java.lang.Integer]
    }
    sub.expectNextPF {
      case x => x shouldBe a [java.lang.Integer]
    }

  }

}
