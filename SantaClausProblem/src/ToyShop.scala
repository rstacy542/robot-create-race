

object ToyShop {
  
  private var elvesInTheShop = List[Elf]()
  
  def startElves(numberOfElves: Int) = {
    for (i <- 1 to numberOfElves) {
      val elf = new Elf()
      elf.start()
      elvesInTheShop = elvesInTheShop ++ List(elf)
    }
    println("Started " + elvesInTheShop.size + " Elves!")
  }
  
  def numberOfElvesInShop = elvesInTheShop.size
}