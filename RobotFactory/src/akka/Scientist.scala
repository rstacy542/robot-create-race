package akka

import akka.actor.Actor

import model.RobotPart
import model.Robot

class Scientist extends Actor {

  override def receive: Receive = {
    case BuildRobot(robotParts, nightlyCycle) =>
      var robot = new Robot()
      val unusedRobotParts = robotParts.filter {bodyPart => robot.isComplete() || !robot.addPart(bodyPart)}
      if (robot.isComplete()) {
        sender ! CompletedRobot(robot, unusedRobotParts, nightlyCycle)
      } else {
        sender ! IncompletePartSetForRobotConstruction
      }
  }
}

case class BuildRobot(robotParts: List[RobotPart], nightlyCycle: Int)
case class CompletedRobot(theRobot: Robot, unusedRobotParts: List[RobotPart], nightlyCycle: Int)
case class IncompletePartSetForRobotConstruction()