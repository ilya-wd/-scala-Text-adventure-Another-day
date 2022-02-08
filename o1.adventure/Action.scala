package o1.adventure


/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as "go east" or "rest" */
class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim


  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as "You go west."). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player) = this.verb match {
    case "go" if (this.commandText == "go some area" && actor.world.additionalArea.hasItem)    => Some(actor.go("some area") + actor.getItem)
    case "go"    => Some(actor.go(this.modifiers))
    case "rest"  => Some(actor.rest())
    case "help"  => Some(actor.help())
    case "quit"  => Some(actor.quit())
    case "get"   => Some(actor.get(this.modifiers))
    case "examine"   => Some(actor.examine(this.modifiers))
    case "drop"   => Some(actor.drop(this.modifiers))
    case "inventory"   => Some(actor.inventory)
    case "sleep" => Some(actor.sleep())
    case other if (this.commandText == "cast fireball")   => Some(actor.castFireball)
    case other if (this.commandText == "use itemname")   => Some(actor.drinkBeer)
    case other if (this.commandText == "interact with an npc")   => Some(actor.takeCookie)
    case other if (actor.location.codeWordDef.contains(this.commandText)) => Some(actor.getItem)
    case other   => None

  }


  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"


}

