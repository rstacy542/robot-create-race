

package model

class RobotPart(val partTypeValue: Int) {
  val robotPartType = RobotPartType(partTypeValue)
  
  override def hashCode: Int = 41 * (41 + partTypeValue)
  
  def canEqual(other: Any): Boolean = other.isInstanceOf[RobotPart]
  
  override def equals(other: Any): Boolean = {
    other match {
      case other: RobotPart =>
        (other canEqual this) &&
        other.robotPartType.id == this.robotPartType.id
      case _ => false
    }
  }
}