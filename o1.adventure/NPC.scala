package o1.adventure

import scala.util.Random

class NPC(startingArea: Area, val name: String, val world: Adventure) {

  var setOfHints = Vector("What does that guy on the right do? https://youtu.be/670BrA4jUoM?t=145", "https://www.youtube.com/watch?v=KYhvVUwU__U", "https://en.wikipedia.org/wiki/Charismatic_Christianity", "https://www.verywellmind.com/say-no-to-people-making-demands-on-your-time-3145025", "omstart enough")

  def giveRandomHint = Random.shuffle(setOfHints(Random.nextInt(setOfHints.size-1)))

  var NPCactivationWord = "interact with an NPC"

  private var currentLocation = startingArea

  // NPC runs at ultrasound speed all the time, and each time in a radnom direction. Thus we call it a teleportation
  def teleport() = {
    val targetArea = this.world.NPCsDirections.filter(_!=this.currentLocation)(Random.nextInt(this.world.NPCsDirections.size-1))
    this.currentLocation.NPCLeft(this)
    this.currentLocation = targetArea
    targetArea.NPCArrived(this)
  }

  val description = "You see a lady in Red Cross clothes peddling fortune cookies out of a ship captain hat. \"Another NPC\" - you think.\n" +
    "If you interact with her, you get a cookie and a paper with fortune."


}
