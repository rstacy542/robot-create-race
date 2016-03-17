
package synchronization

object RobotFactorySimulator extends App {
  
  val robotFactory = new DroidskyRobotFactoryDump(20)
  val frankensteelMansion = new Mansion("Frankensteel")
  val frankenstealMansion = new Mansion("Frankensteal")
        
  private var currentNightlyCycle = 1
  val nightlyCycleLock = new Object()

  println("Nightly Cycle: " + currentNightlyCycle)

  robotFactory.start()
  frankensteelMansion.start()
  frankenstealMansion.start()

  while (currentNightlyCycle < 100) {
    java.lang.Thread.sleep(100)
    nightlyCycleLock.synchronized {
      currentNightlyCycle += 1
      nightlyCycleLock.notifyAll()
    }
    println("Nightly Cycle: " + currentNightlyCycle)
  }
  java.lang.Thread.sleep(100)
  
  robotFactory.join(100)
  frankensteelMansion.join(100)
  frankenstealMansion.join(100)
  
  def nightlyCycleStarted(nightlyCycle: Int) : Boolean = {
    nightlyCycleLock.synchronized {
      if (currentNightlyCycle < nightlyCycle) {
        nightlyCycleLock.wait()
        nightlyCycleLock.notifyAll()
      } 
    }
    true
  }
}