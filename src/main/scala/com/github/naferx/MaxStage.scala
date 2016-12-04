package com.github.naferx

import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import akka.stream.stage.{GraphStage, GraphStageLogic, InHandler, OutHandler}


final class MaxStage extends GraphStage[FlowShape[Int, Int]] {

  val in = Inlet[Int]("MaxStage.in")
  val out = Outlet[Int]("MaxStage.out")

  override val shape: FlowShape[Int, Int] = FlowShape.of(in, out)

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) with InHandler with OutHandler {

      var maxValue = Int.MinValue
      var maxPushed = Int.MinValue

      override def onPush(): Unit = {
        maxValue = math.max(maxValue, grab(in))
        if (isAvailable(out) && maxValue > maxPushed)
          pushMaxValue()
        pull(in)
      }


      override def onPull(): Unit = {
        if (maxValue > maxPushed)
          pushMaxValue()
        else if (!hasBeenPulled(in))
          pull(in)
      }


      override def onUpstreamFinish(): Unit = {
        if (maxValue > maxPushed)
         //pushMaxValue()
          emit(out, maxValue)
        completeStage()
      }


      def pushMaxValue(): Unit = {
        maxPushed = maxValue
        push(out, maxPushed)
      }

    setHandlers(in, out, this)
  }
}
