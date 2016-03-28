
import java.util.concurrent.Semaphore

class SantaClaus extends Thread {

  override def run() = {
    while (SantaClausProblem.numberOfChristmasCelebrated < 10) {
      SantaClaus.IsAwake.synchronized {
        if (!SantaShop.allReindeerBackFromVacation && !SantaShop.threeElvesAreWaiting()) {
          println("Santa Claus is going back to sleep!")
          SantaClaus.IsAwake.wait()
        }
      }

      if (SantaShop.allReindeerBackFromVacation) {
        println("Preparing for Christmas!")
        SantaClaus.ReindeerHookedUpToSled.release(9)
        SantaClaus.SledIsReady.synchronized {
          SantaClaus.SledIsReady.wait()
        }
        SantaClausProblem.ChristmasIsHere.synchronized {
          SantaClausProblem.ChristmasIsHere.wait()
        }
        println("Deliver Christmas")
        SantaShop.celebrateChristmasDelivery()
      } else if (SantaShop.threeElvesAreWaiting()) {
        println("Helping Elves")
        while (SantaShop.threeElvesAreWaiting()) {
          SantaClaus.GetHelpFromSanta.release(3)
          SantaClaus.HelpedElvesWithToyProblem.acquire(3)
          println("Helped 3 elves with toy problem")
          SantaShop.helpedAllThreeElves()
        }
      } else {
        println("NO ONE WAITING!")
      }
    }
  }
}
object SantaClaus {
  val IsAwake = new Object()
  val GetHelpFromSanta = new Semaphore(0, true)
  val ReindeerHookedUpToSled = new Semaphore(0)
  val HelpedElvesWithToyProblem = new Semaphore(0)
  val SledIsReady = new Object()
}