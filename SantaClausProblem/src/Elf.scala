
import scala.util.Random

class Elf extends Thread {
  private val randomGenerator = new Random()
  
  override def run() = {
    while (SantaClausProblem.numberOfChristmasCelebrated < 10) {
      val timeToWait = nextCheckIfElfHasProblemInMilliseconds
      java.lang.Thread.sleep(timeToWait)
      
      if (elfHasProblem) {
        SantaShop.requestHelpFromSanta()
      }
    }
  }
  
  def nextCheckIfElfHasProblemInMilliseconds(): Long = {
    val problemsIncreaseCloserToXMasFactor: Double = 2.0 - (SantaClausProblem.DaysBeforeChristmas.toDouble/240.00) 
    val maxTimeToWaitForCheck = SantaClausProblem.MILLISECONDS_IN_A_DAY/problemsIncreaseCloserToXMasFactor  
    randomGenerator.nextInt(maxTimeToWaitForCheck.toInt) + 1
  }
  
  def elfHasProblem(): Boolean = {
    val problemsIncreaseCloserToXMasFactor = (SantaClausProblem.DAYS_IN_A_YEAR - SantaClausProblem.DaysBeforeChristmas)/2
    val randomNumberBetween1And1000 = randomGenerator.nextInt(20000) + 1
    randomNumberBetween1And1000 < problemsIncreaseCloserToXMasFactor + 1
  }
}