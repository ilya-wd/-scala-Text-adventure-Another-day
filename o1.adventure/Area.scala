package o1.adventure

import scala.collection.mutable
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map

// The class `Area` represents locations in a text adventure game world.
class Area(var name: String, var description: String, val world: Adventure) {

  private val neighbors = Map[String, Area]()

  private def nightTimeDirections = Map("your apartment" -> this.world.yourRoom, "house party" -> this.world.houseParty)

  private var beer = false

  private var beerDescription: Option[String] = None

  private var specialItems = Map[String, Item]()

  private var item: Option[Item] = None

  private var specialItemDescription: Option[String] = None

  private var codeWord: Option[String] = None

  var puzzleHint: Option[String] = None

  var walkingNPC: Option[NPC] = None

  var NPCword: Option[String] = None


  // For Room location only. Items automatically added to the player's "base" - their room
  def stashItem(item: Item) = {
    this.specialItems += item.name -> item
  }

  def addItem(newItem: Item, newText: String) = {
    this.item = Option(newItem)
    this.specialItemDescription = Option(newText)
    this.codeWord = Option(newItem.codeWord)
    this.puzzleHint = Option(newItem.hint)
  }

  def contains(itemName: String) = {
    this.specialItems.contains(itemName)
  }

  def removeItem() = {
    this.codeWord = None
    this.item = None
  }


  /** Returns the area that can be reached from this area by moving in the given direction. The result
    * is returned in an `Option`; `None` is returned if there is no exit in the given direction. */
  def neighbor(direction: String) = {
    if (this.world.timeIsUp) this.nightTimeDirections.get(direction)
    else this.neighbors.get(direction)
  }


  /** Adds an exit from this area to the given area. The neighboring area is reached by moving in
    * the specified direction from this area. */
  def setNeighbor(direction: String, neighbor: Area) = {
    this.neighbors += neighbor.name -> neighbor
  }


  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling
    * the `setNeighbor` method on each of the given direction--area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]] */
  def setNeighbors(exits: Vector[(String, Area)]) = {
    this.neighbors ++= exits
  }

  def addBeer(newText: String) = {
    this.beer = true
    this.beerDescription = Option(newText)
  }

  def removeBeer() = this.beer = false

  def hasBeers = beer

  // If the NPC in an area, then a player can interact with it
  def NPCArrived(arrivedNPC: NPC) = {
    this.walkingNPC = Option(arrivedNPC)
    this.NPCword = Option(arrivedNPC.NPCactivationWord)
  }

  def NPCLeft(leftNPC: NPC) = {
    this.walkingNPC = None
    this.NPCword = None
  }




  /** Returns a multi-line description of the area as a player sees it. This includes a basic
    * description of the area as well as information about exits and items. The return
    * value has the form "DESCRIPTION\n\nExits available: DIRECTIONS SEPARATED BY SPACES".
    * The directions are listed in an arbitrary order. */
  def fullDescription = {
    var exitList =
      if (this.name == "Room" && this.world.timeIsUp) {
        "\nIt's a night time, and lucky for you, you're at your domicile, where the jumpsuit creatures can't reach you. You won't leave your apartment until morning."
      }
      else if (this.world.timeIsUp) {
      "\n\nIt's almost night! The jumpsuit creatures will be out and about suit. Finish your business here and run home. Watch out for flamboyant outifts!" +
        "\nOr you can go to a friend's house party and spend a night there." + s"\nFor now, you hide somewhere around ${this.name}" + "\n\nExits available: " + this.nightTimeDirections.keys.mkString(" | ") + "\n\n"
    } else "\n\nExits available: " + this.neighbors.keys.mkString(" | ") + "\n\n"

    var endDescription = exitList
    if (this.beer) endDescription+= this.beerDescription.get
    if (this.item.isDefined) {
      endDescription += "\n\n" + this.specialItemDescription.get + "\n\n" + s"Free hint: the answer is ${codeWord.get.split(" ").size} words long." + "\n"
      }  else endDescription+= "\n\n" + this.description
    if (this.walkingNPC.isDefined) {
      endDescription = endDescription + "\n\n" + this.walkingNPC.get.description + "\n" + s"You can use ${NPCword.get} to intereact with ${walkingNPC.get.name}."
      }
    if (this.name == "Room" && this.specialItems.nonEmpty) endDescription += "\n\nThe following items adorn your room: \n" + this.specialItems.keys.mkString("\n") + "\nDon't be afraid, go on and examine the items."
    endDescription + "\n\n[Please scroll up if needed]"

  }

  def hasItem = {
    this.item.isDefined
  }

  def specialItemsDef = this.specialItems

  def codeWordDef = this.codeWord

  def itemsNumber = this.specialItems.size

  def localItem = this.item


  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)



}
