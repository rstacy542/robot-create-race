package akka

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef

import scala.util.Random

import model.Robot
import model.RobotPart

class Mansion(val mansionName: String, theDroidskyRobotFactoryDump: ActorRef) extends Actor {
  private val randomGenerator = new Random()
  var theRobotParts = List[RobotPart]()
  var robots = List[Robot]()
  var currentNightlyCycle = 0
  
  override def receive: Receive = {
    case StartNightlyCycle(nightlyCycle) =>
      println(mansionName + " starting nightly cycle " + nightlyCycle)
      currentNightlyCycle = nightlyCycle
      val numberOfPartsToRetrieve = randomGenerator.nextInt(4) + 1
      val theMinion = RobotFactoryAkkaSimulator.system.actorOf(Props(new Minion(mansionName, nightlyCycle, theDroidskyRobotFactoryDump)))
      theMinion ! FetchPartsFromDump(numberOfPartsToRetrieve)
    case FetchedRobotParts(robotParts, nightlyCycle) =>
      println(mansionName + " retrieved " + robotParts.size + " body parts for nightly cycle " + nightlyCycle)
      theRobotParts = theRobotParts ++ robotParts
      val theScientist = RobotFactoryAkkaSimulator.system.actorOf(Props(new Scientist()))
      theScientist ! BuildRobot(theRobotParts, nightlyCycle)
    case CompletedRobot(robot, unusedRobotParts, nightlyCycle) =>
      if (nightlyCycle == currentNightlyCycle) {
        theRobotParts = unusedRobotParts
        robots = robots ++ List(robot)
        println(mansionName + " number of robots: " + robots.size)
        val theScientist = RobotFactoryAkkaSimulator.system.actorOf(Props(new Scientist()))
        theScientist ! BuildRobot(theRobotParts, nightlyCycle)
      }
    case IncompletePartSetForRobotConstruction =>
    case ReportRobotStatistics =>
      println(mansionName + " number of robots: " + robots.size + " and number of unused parts: " + theRobotParts.size)
      
  }
}
object Mansion {
  def props(mansionName: String, theDroidskyRobotFactoryDump: ActorRef): Props = Props(new Mansion(mansionName, theDroidskyRobotFactoryDump))
}

case class FetchedRobotParts(robotParts: List[RobotPart], nightlyCycle: Int)
case class ReportRobotStatistics()