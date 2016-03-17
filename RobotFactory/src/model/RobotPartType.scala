

package model

object RobotPartType extends Enumeration{
  val HEAD = Value(0, "Head")
  val BODY = Value(1,  "Body")
  val LEFT_ARM = Value(2, "Left Arm")
  val RIGHT_ARM = Value(3, "Right Arm")
  val LEFT_LEG = Value(4, "Left Leg")
  val RIGHT_LEG = Value(5, "Right Leg")
  val CPU = Value(6, "CPU")
  val RAM = Value(7, "RAM")
  val HARD_DRIVE = Value(8, "Hard Drive")
}