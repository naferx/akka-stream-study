package com.github.naferx

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink, Source}
import akka.stream.testkit._
import akka.stream.testkit.scaladsl.{TestSink, TestSource}
import org.scalatest.{FlatSpec, Matchers}


final class RandomNumberSourceSpec extends FlatSpec with Matchers {

  implicit val system = ActorSystem("testing")
  implicit val materializer = ActorMaterializer.create(system)


  "RandomNumberSource" should "produce a random number" in {
    val source = new RandomNumberSource

    Source.fromGraph(source)
          .runForeach(println)



  }

}
