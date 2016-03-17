package synchronization

import scala.collection.immutable.List
import model.Robot
import model.RobotPart

class Mansion(val mansionName: String) extends Thread {
  
  var nightlyCycle = 1
  var bodyParts = List[RobotPart]()
  var robots = List[Robot]()
  var partiallyBuiltRobot: Robot = new Robot()
  
  val theMinion = new Minion()
  
  override def run() = {
    while (nightlyCycle <= 100 && RobotFactorySimulator.nightlyCycleStarted(nightlyCycle)) {
      println(mansionName + " starting nightly cycle " + nightlyCycle)
      val retrievedParts = theMinion.retrievePartsFromDump(nightlyCycle)
      println(mansionName + " retrieved " + retrievedParts.size + " body parts for nightly cycle " + nightlyCycle)
  
      val unusedParts = buildRobotWith(retrievedParts)
      bodyParts = bodyParts ++ unusedParts
       
      while (partiallyBuiltRobot.isRobotComplete()) {
        robots = robots ++ List(partiallyBuiltRobot)
        partiallyBuiltRobot = new Robot()
        bodyParts = buildRobotWith(bodyParts)
      }
  
      println(mansionName + " number of robots: " + robots.size)
      nightlyCycle += 1
    }
  }
  
  /**
   * Takes in a list of available robot parts and tries to build a robot.  It will return the list of unusable parts.
   */
  def buildRobotWith(availableRobotParts: List[RobotPart]): List[RobotPart] = {
    availableRobotParts.filter {bodyPart => partiallyBuiltRobot.isRobotComplete() || !partiallyBuiltRobot.addPart(bodyPart)}
  }
}