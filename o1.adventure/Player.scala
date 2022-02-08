package o1.adventure

import scala.collection.mutable.Map


/** A `Player` object represents a player character controlled by the real-life user of the program.
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player */
class Player(startingArea: Area, val world: Adventure) {

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private val items = Map[String, Item]()
  private var beers = 0
  private var beerDrunkDaily = 0

  // is a condition for losing the game
  var isDrunk = false
  // is a condition for when text gets shuffled
  var isTipsy = false

  var wentToParty = false

  def drop(itemName: String) = {
    if (currentLocation.name == "Room") {
      this.currentLocation.stashItem(this.items.remove(itemName).get)
      s"You drop the ${itemName}."
    } else if (items.contains(itemName)) s"You worked too hard to get ${itemName}. If you leave it here, someone will steal it immediately. Gotta carry it with you"
      else if (itemName == "beer")  "Wait, wait, what are you doing? You know it's a criminal offence to drop a beer? You'll have to keep it."
      else s"You don't have that! If you want to drop ${itemName} you must find it first."
  }

  def examine(itemName: String) = {
    if (items.contains(itemName)) {
      s"You look closely at the ${itemName}.\n${items(itemName).description}"
    } else if (currentLocation.name == "Room" && currentLocation.contains(itemName)) {
      s"You look closely at the ${itemName}.\n${currentLocation.specialItemsDef(itemName).description}"
    }
    else "If you want to examine something, you need to pick it up first."
  }

  // Only beer can be taken directly in the game
  def get(itemName: String) = {
    if (this.currentLocation.hasBeers && itemName == "beer") {
      this.currentLocation.removeBeer()
      this.beers += 1
      s"You take a can of Karcoq beer."
    } else s"There is no ${itemName} here to pick up."
  }

  def inventory = {
    var inventoryDescription = "You spend an hour digging in your bag only to realize that "
    if (this.items.isEmpty) {
      inventoryDescription += "you don't have any interesting items with you now."
    } else {
      inventoryDescription += s"you are carrying: ${this.items.keys.foldLeft("\n")((string, next)=> string+"\n"+next)}\n"
    }
    if (this.beers == 0) inventoryDescription += "\n\nNo beers... yet or already."
    else inventoryDescription += s"\nYou count your beers: \n${this.beers} can(s) of liquid gold"
    inventoryDescription
  }

  def help() = {
    "You kinda forgot how to live the life. You get a cribsheet from you pocket and read the following:\n" +
      "\nuse itemname: if you want to drink a beer. Don't ask why it's phrased that way" +
      "\ngo %exit_name%: if you want to go somewhere" +
      "\nrest: if you want to rest for a bit" +
      "\nsleep: if want to sleep" +
      "\nexamine %item_name%: if you want to check that cool item you now own" +
      "\ninventory: if you want to check what you're carrying" +
      "\ninteract with an npc: if you see a person you can talk with" +
      "\ndrop: if you want to drop an item" +
      "\nget: if you want to get an item. Beer, in particular" +
      "\ncast fireball: if you want to cast fireball" +
      "\nhelp: if you want to read this crib sheet once again" +
      "\nquit: if you want to quit to an alternate reality"
  }


  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven


  /** Returns the current location of the player. */
  def location = this.currentLocation


  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player's current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) = {
    val destination = this.currentLocation.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if (destination.isDefined) "Yes go to: " + direction + "." else "No go to: " + direction + "."
  }


  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() = {
    if (this.currentLocation.name == "Room") {
      this.world.dayCount += 1
      this.world.currentTime = 8
      this.beerDrunkDaily = 0
      this.isTipsy = false
      "You decided to rest for a bit in your bed. You fell asleep. \nYou wake up the next morning." +
        "\nAnother day."
    } else
    "You rest for a while. Better get a move on, though."
  }

  // "use itemname" command
  def drinkBeer = {
    if (this.beers != 0) {
      this.beers -= 1
      this.beerDrunkDaily += 1

      if (this.beerDrunkDaily == 3) {
        this.isTipsy = true
        s"Another beer.\n\nTotal for today:${this.beerDrunkDaily}. \nYou drank too much for today, my dear. Perhaps you should stop now and go get some sleep? \n\nTry to remember how to get to your apartment from here. Watch the time."
      } else if (this.beerDrunkDaily >= 4) {
          this.isDrunk = true
          s"You drink another beer.\nTotal for today:${this.beerDrunkDaily}."
        } else {
            if (this.currentLocation.hasItem && this.currentLocation.name != "Room") {
              s"That cold one really boosted your mind. \nTotal for today:${this.beerDrunkDaily}. \nThe following characters popup in your mind:\n " + this.currentLocation.puzzleHint.get
            }
            else s"You drink another beer. \nTotal for today:${this.beerDrunkDaily}."
          }

    }
    else "You have no *itemToUse*. Go find it!"
  }

  // interaction with an NPC. If it is in the current area - a special action
  // "interact with an NPC command"
  def takeCookie = {
    if (this.currentLocation.walkingNPC.isDefined) {
      "\n\n" + "You get a cookie, crack it, extract the paper, eat the cookie and read the paper: " +
      "\n\n" + this.currentLocation.walkingNPC.get.giveRandomHint + "\n\n" +
        "\"What's that? Am I a PhD in cryptography or something to understand this?\" But the NPC has already disappeared. Looks like you have to make do with what you got. " +
        "Get it or leave. \nWell, you already got it, so all you can do now is to leave."
    }
    else "You see any cookies here? No. Can't get a cookie."
  }


  def sleep() = {
    if (this.currentLocation.name == "Room") {
      this.world.dayCount += 1
      this.world.currentTime = 8
      this.beerDrunkDaily = 0
      this.isTipsy = false
      "You fall asleep in your – yet – not-so-cozy room. You wake up in the same room (who would've thought!) the next morning." +
        "\nAnother day."
    } else
    s"You try to find a place to sleep in ${this.currentLocation.name} but to no avail. No sleep for you here"
  }

  // Transferring items from player's inventory to room
  def transferItems = {
    if (this.items.nonEmpty && this.currentLocation.name == "Room") {
      this.items.map(pair=> drop(pair._1))
    }
  }

  def getItem = {
    if (this.currentLocation.hasItem) {
      var newItem = this.currentLocation.localItem.get
      this.items += newItem.name -> newItem
      this.currentLocation.removeItem()
      "\n" + newItem.answerText +
      s"\n\nNow you're in possession of ${newItem.name}. Don't lose it!"
    }
  }

  def castFireball = {
    if (this.currentLocation.name != "Room")
    s"\nYou channel all your energy and focus it on the nearest pigeon. \n...\n...\n...\nYou visualize the sun, its pure essence, and the core of the Earth..." +
      s"\nFinally, when you feel ready, you cast the magic spell on that pigeon with your eyes closed...\nAnd... \nNothing happens!" +
      s"\n\nPerhaps you thought for a second that it's a Dungeon and Dragons universe, but it's not. It's just a real life out there, with people staring at you." +
      s"\nOk, you had some fun, now continue with your day and ordinary life."
    else "No, you don't want to set your own room on fire."
  }

  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }

  def beersForToday = this.beerDrunkDaily


  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.currentLocation.name


}


