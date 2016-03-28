import java.util.Calendar

object SantaClausProblem extends App {
  val MILLISECONDS_IN_A_DAY = 48
  val DAYS_IN_A_YEAR = 365
  val startTime = Calendar.getInstance.getTimeInMillis
  var Christmas = startTime + (MILLISECONDS_IN_A_DAY * DAYS_IN_A_YEAR)
  val ChristmasTimeUpdateLock = new Object()
  val ChristmasIsHere = new Object()
  
  var numberOfChristmasCelebrated = 0
  
  def DaysBeforeChristmas = {
    if (Christmas < Calendar.getInstance.getTimeInMillis) {
      ChristmasTimeUpdateLock.synchronized {
        if (Christmas < Calendar.getInstance.getTimeInMillis) {
          ChristmasIsHere.synchronized {
            println("Christmas Has Arrived!")
            Christmas = Christmas + (MILLISECONDS_IN_A_DAY * DAYS_IN_A_YEAR)
            numberOfChristmasCelebrated += 1
            ChristmasIsHere.notify()
          }
        }
      }
      
    }
    (Christmas - Calendar.getInstance.getTimeInMillis)/MILLISECONDS_IN_A_DAY
  }
  
  ToyShop.startElves(1000) 
  val theSantaShop = new SantaShop()
  
  while (ToyShop.numberOfElvesInShop > 0) {
    java.lang.Thread.sleep(MILLISECONDS_IN_A_DAY)
  }
  
  theSantaShop.theSantaClaus.join()
}