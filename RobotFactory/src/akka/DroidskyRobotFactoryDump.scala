

package akka

import akka.actor.Actor
import akka.actor.Props

import scala.collection.mutable.Queue
import scala.util.Random

import model.RobotPart

class DroidskyRobotFactoryDump(val initialNumberOfParts: Int) extends Actor {
  private val availableRobotParts = Queue[RobotPart]()
  private val randomGenerator = new Random()
  private var lastCyclePartsGeneratedFor = 0
  private var CurrentNightlyCycle = 0
  
  addBodyParts(initialNumberOfParts)

  override def receive: Receive = {
    case StartNightlyCycle(nightlyCycle) =>
      CurrentNightlyCycle = nightlyCycle
      println("Starting robot factory dump cycle " + nightlyCycle + " with " + availableRobotParts.size + " parts")  
      val numberOfPartsToAdd = randomGenerator.nextInt(4) + 1
      addBodyParts(numberOfPartsToAdd)
      lastCyclePartsGeneratedFor += 1
      println("Droidsky Robot Factory Dumped " + numberOfPartsToAdd + " Robot Parts for nightly cycle : " + nightlyCycle)
    case TakePart(nightlyCycle) =>
      if (nightlyCycle < CurrentNightlyCycle) {
        sender ! DumpClosedForTheNight
      } else if (availableRobotParts.size > 0) {
        sender ! FetchedRobotPart(availableRobotParts.dequeue())
      } else if (nightlyCycle > lastCyclePartsGeneratedFor) {
        sender ! PartsCurrentlyUnavailable
      } else {
        sender ! PartUnavailableGenerationComplete
      }
  }
  
  
  private def addBodyParts(numberOfPartsToAdd: Int) = {
    for (i <- 1 to numberOfPartsToAdd) {
      addRobotPart()
    }  
  }
  
  private def addRobotPart() = {
    availableRobotParts.enqueue(new RobotPart(randomGenerator.nextInt(9)))
  }

}
object DroidskyRobotFactoryDump {
  def props(initialNumberOfParts: Int): Props = Props(new DroidskyRobotFactoryDump(initialNumberOfParts))
}

case class StartNightlyCycle(nightlyCycle: Int) 
case class TakePart(nightlyCycle: Int)
case class FetchedRobotPart(theRobotPart: RobotPart)
case class PartUnavailableGenerationComplete()
case class PartsCurrentlyUnavailable()
case class DumpClosedForTheNight()