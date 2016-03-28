
import scala.util.Random

class Reindeer extends Thread {
  private val randomGenerator = new Random()
  
  override def run() = {
    while (SantaClausProblem.numberOfChristmasCelebrated < 10) {
      java.lang.Thread.sleep(SantaClausProblem.MILLISECONDS_IN_A_DAY)
      
      if (reindeerReturnedFromVacation) {
        println("Reindeer has come back from vacation")
        SantaShop.reindeerReportsForDuty()
      }
    }
  }
  
  def reindeerReturnedFromVacation(): Boolean = {
    var chancesIncreaseCloserToXMasFactor = (356 - SantaClausProblem.DaysBeforeChristmas)/120
    if (SantaClausProblem.DaysBeforeChristmas <= 25) {
      chancesIncreaseCloserToXMasFactor += (30 - SantaClausProblem.DaysBeforeChristmas) * 3
    }
    val randomNumberBetween1And1000 = randomGenerator.nextInt(110) + 1
    randomNumberBetween1And1000 < chancesIncreaseCloserToXMasFactor
  }
  
}