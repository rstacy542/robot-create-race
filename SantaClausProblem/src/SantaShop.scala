
import java.util.concurrent.Semaphore

class SantaShop {
  val theSantaClaus = new SantaClaus()
  private var reindeer = List[Reindeer]()
  
  for (i <- 1 to 9) {
    val theReindeer = new Reindeer()
    theReindeer.start()
    reindeer = reindeer ++ List(theReindeer)
  }
  
  println("Started " + reindeer.size + " Reindeer!")

  SantaClaus.IsAwake.synchronized {
    theSantaClaus.start()
    println("Started Santa Claus")
  }
}
object SantaShop {
  private val requestHelpFromSantaClaus = new Semaphore(3, true)
  val allElfReadyForHelp = new Object()
  private val reindeerBackFromVacation = new Semaphore(0)
  private val AllReindeerBackFromVacation = new Object()
  private val ticketsToTropicalIslandVacation = new Semaphore(0)
  
  def reindeerReportsForDuty() {
    
    AllReindeerBackFromVacation.synchronized {
      reindeerBackFromVacation.release()
      if (reindeerBackFromVacation.availablePermits() == 9) {
        println("All Reindeer Have Returned!")
        SantaClaus.IsAwake.synchronized {
          SantaClaus.IsAwake.notify()
        }
      }
    }
    
    SantaClaus.ReindeerHookedUpToSled.acquire()
    SantaClaus.SledIsReady.synchronized {
      if (SantaClaus.ReindeerHookedUpToSled.availablePermits() == 0) {
        SantaClaus.SledIsReady.notify()
      }
    }
    
    ticketsToTropicalIslandVacation.acquire()
    println("GOING ON VACATION!")
  }
  
  def allReindeerBackFromVacation = reindeerBackFromVacation.availablePermits() == 9
  
  def requestHelpFromSanta() {
    
    if (requestHelpFromSantaClaus.hasQueuedThreads()) {
      println("There are " + (requestHelpFromSantaClaus.getQueueLength + 1) + " elves waiting")
    }
    val ticket = requestHelpFromSantaClaus.acquire()
    
    SantaClaus.IsAwake.synchronized {
      if (threeElvesAreWaiting) {
        SantaClaus.IsAwake.notify()
      }
    } 
    SantaClaus.GetHelpFromSanta.acquire()
    println("Getting Help From Santa")
    SantaClaus.HelpedElvesWithToyProblem.release()
  }
  
  def threeElvesAreWaiting(): Boolean = requestHelpFromSantaClaus.availablePermits() == 0
  
  def helpedAllThreeElves() = requestHelpFromSantaClaus.release(3)
  
  def celebrateChristmasDelivery() = {
    println("Celebrate Christmas!")
    ticketsToTropicalIslandVacation.release(9)
    println("Reindeer going on vacation!")
    reindeerBackFromVacation.acquire(9)
    println("Are all reindeer back from vacation: " + allReindeerBackFromVacation)
    
  }
}