

package model

import scala.collection.mutable.HashSet

class Robot {
  var robotParts = HashSet[RobotPart]()
  
  private def hasPart(part: RobotPart): Boolean = robotParts.contains(part)
  
  def addPart(part: RobotPart): Boolean = {
    if (hasPart(part)) {
      false
    } else {
      robotParts += part
      true
    }
  }
  
  def isComplete(): Boolean = robotParts.size == 9
}