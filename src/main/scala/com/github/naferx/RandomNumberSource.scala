package com.github.naferx

import java.util.Random

import akka.stream.{Attributes, Outlet, SourceShape}
import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}


final class RandomNumberSource extends GraphStage[SourceShape[Int]] {

  val outlet = Outlet[Int]("RandomNumberSource.out")

  override def shape: SourceShape[Int] = SourceShape.of(outlet)

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) with OutHandler {

      val random = new Random()

      override def onPull(): Unit = {
        push(outlet, random.nextInt())
      }

      setHandler(outlet, this)
    }

}
